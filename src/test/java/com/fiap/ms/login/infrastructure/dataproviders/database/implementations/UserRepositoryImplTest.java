package com.fiap.ms.login.infrastructure.dataproviders.database.implementations;

import com.fiap.ms.login.application.gateways.JpaUserRepositoryGateway;
import com.fiap.ms.login.domain.model.User;
import com.fiap.ms.login.infrastructure.dataproviders.database.entities.JpaUserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserRepositoryImplTest {

    @Mock
    private JpaUserRepositoryGateway jpaUserRepositoryGateway;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Session session;

    @Mock
    private Filter filter;

    @InjectMocks
    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {
        // Ensure fresh mock setup for each test
        when(entityManager.unwrap(Session.class)).thenReturn(session);
        when(session.enableFilter("deletedFilter")).thenReturn(filter);
        when(filter.setParameter("isDeleted", false)).thenReturn(filter);
    }

    @Test
    void save_shouldSaveUser() {
        User user = new User();
        user.setName("Test User");
        JpaUserEntity entity = new JpaUserEntity(user);
        when(jpaUserRepositoryGateway.save(any(JpaUserEntity.class))).thenReturn(entity);

        User result = userRepository.save(user);

        assertNotNull(result);
        verify(jpaUserRepositoryGateway).save(any(JpaUserEntity.class));
    }

    @Test
    void delete_shouldCallRepository() {
        Long userId = 1L;
        JpaUserEntity entity = new JpaUserEntity();
        entity.setId(userId);
        
        when(jpaUserRepositoryGateway.findById(userId)).thenReturn(Optional.of(entity));
        when(jpaUserRepositoryGateway.save(entity)).thenReturn(entity);

        userRepository.delete(userId);

        assertTrue(entity.isDeleted());
        verify(jpaUserRepositoryGateway).save(entity);
    }

    @Test
    void update_shouldUpdateUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Updated User");
        user.setEmail("updated@test.com");
        
        JpaUserEntity existingEntity = new JpaUserEntity();
        existingEntity.setId(1L);
        existingEntity.setName("Old Name");
        
        when(jpaUserRepositoryGateway.findById(1L)).thenReturn(Optional.of(existingEntity));
        when(jpaUserRepositoryGateway.save(any(JpaUserEntity.class))).thenReturn(existingEntity);

        User result = userRepository.update(user);

        assertNotNull(result);
        verify(jpaUserRepositoryGateway).findById(1L);
        verify(jpaUserRepositoryGateway).save(any(JpaUserEntity.class));
    }

    @Test
    void update_shouldThrowExceptionWhenUserNotFound() {
        User user = new User();
        user.setId(1L);
        
        when(jpaUserRepositoryGateway.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userRepository.update(user));
    }

}