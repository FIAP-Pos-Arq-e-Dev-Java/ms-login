package br.com.fiap.wefood.adapters.outbound.repositories;

import br.com.fiap.wefood.adapters.outbound.repositories.entities.JpaAddressEntity;
import br.com.fiap.wefood.adapters.outbound.repositories.entities.JpaUserEntity;
import br.com.fiap.wefood.domain.user.Address;
import br.com.fiap.wefood.domain.user.User;
import br.com.fiap.wefood.domain.user.UserRepository;
import br.com.fiap.wefood.utils.mapper.AddressMapper;
import br.com.fiap.wefood.utils.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.hibernate.Session;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;
    private final EntityManager entityManager;


    public UserRepositoryImpl(
            JpaUserRepository jpaUserRepository,
            EntityManager entityManager
    ) {
        this.jpaUserRepository = jpaUserRepository;
        this.entityManager = entityManager;
    }

    @Override
    public User save(User user) {
        JpaUserEntity userEntity = new JpaUserEntity(user);
        JpaUserEntity savedUser = jpaUserRepository.save(userEntity);
        return UserMapper.entityToDomain(savedUser);
    }

    @Override
    public User update(User user) {
        JpaUserEntity userEntity = jpaUserRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // update user fields
        userEntity.setName(user.getName());
        userEntity.setEmail(user.getEmail());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());

        // Update address fields
        if(user.getAddress() != null) {
            JpaAddressEntity addressEntity = userEntity.getAddress();
            Address domainAddress = user.getAddress();

            if (addressEntity == null) {
                // User had no address, create new:
                addressEntity = AddressMapper.domainToEntity(domainAddress, userEntity);
                userEntity.setAddress(addressEntity);
            } else {
                // User already has address, update fields:
                addressEntity.setStreet(domainAddress.getStreet());
                addressEntity.setNumber(domainAddress.getNumber());
                addressEntity.setComplement(domainAddress.getComplement());
                addressEntity.setCity(domainAddress.getCity());
                addressEntity.setState(domainAddress.getState());
            }
        }

        JpaUserEntity savedUser = jpaUserRepository.save(userEntity);
        return UserMapper.entityToDomain(savedUser);
    }

    @Override
    public List<User> findAllUsers(Integer page, Integer size) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter").setParameter("isDeleted", false);
        Pageable pageable = PageRequest.of(page, size);
        return jpaUserRepository.findAll(pageable).stream().map(UserMapper::entityToDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(UUID id) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter").setParameter("isDeleted", false);
        return jpaUserRepository.findById(id).map(UserMapper::entityToDomain);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter").setParameter("isDeleted", false);
        return jpaUserRepository.findByUsername(username).map(UserMapper::entityToDomain);
    }

    @Override
    public void delete(UUID id) {
        JpaUserEntity userEntity = jpaUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        userEntity.setDeleted(true);
        jpaUserRepository.save(userEntity);
    }
}
