package com.trekko.api.repositories;

import com.trekko.api.models.User;
import dev.morphia.Datastore;
import dev.morphia.query.Query;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private Datastore datastore;
    @Mock
    private Query<User> query;

    @InjectMocks
    private UserRepository userRepository;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(datastore.find(User.class)).thenReturn(query);
    }

    @Test
    void saveUser_savesUserSuccessfully() {
        final User user = new User();
        when(datastore.save(any(User.class))).thenReturn(user);

        User savedUser = userRepository.saveUser(user);
        verify(datastore, times(1)).save(userArgumentCaptor.capture());
        assertEquals(user, userArgumentCaptor.getValue());
        assertNotNull(savedUser);
    }

    @Test
    void findUserById_findsUser() {
        final ObjectId id = new ObjectId();
        final User user = new User();
        user.setId(id);

        when(query.stream()).thenReturn(Stream.of(user));

        final User found = userRepository.findUserById(id);

        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    void findUserByEmail_findsUser() {
        final String email = "test@example.com";
        final User user = new User();
        user.setEmail(email);

        when(query.stream()).thenReturn(Stream.of(user));

        final User found = userRepository.findUserByEmail(email);

        assertNotNull(found);
        assertEquals(email, found.getEmail());
    }

    @Test
    void deleteUser_deletesUser() {
        final User user = new User();
        userRepository.deleteUser(user);
        verify(datastore, times(1)).delete(user);
    }

    @Test
    void existsByEmail_checksExistence() {
        final String email = "exists@example.com";
        final User user = new User();
        user.setEmail(email);

        when(query.stream()).thenReturn(Stream.of(user));

        boolean exists = userRepository.existsByEmail(email);

        assertTrue(exists);
    }
}
