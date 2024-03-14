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

class UserRepositoryTests {

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
    public void saveUser_savesUserSuccessfully() {
        final User user = new User();
        when(datastore.save(any(User.class))).thenReturn(user);

        User savedUser = userRepository.saveUser(user);
        verify(datastore, times(1)).save(userArgumentCaptor.capture());
        assertEquals(user, userArgumentCaptor.getValue());
        assertNotNull(savedUser);
    }

    @Test
    public void findUserById_findsUser() {
        final ObjectId id = new ObjectId();
        final User user = new User();
        user.setId(id);

        when(query.stream()).thenReturn(Stream.of(user));

        final User found = userRepository.findUserById(id);

        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void findUserById_returnsNullIfNotFound() {
        final ObjectId id = new ObjectId();

        when(query.stream()).thenReturn(Stream.empty());

        final User found = userRepository.findUserById(id);
        assertNull(found);
    }

    @Test
    public void findUserByEmail_findsUser() {
        final String email = "test@example.com";
        final User user = new User();
        user.setEmail(email);

        when(query.stream()).thenReturn(Stream.of(user));

        final User found = userRepository.findUserByEmail(email);

        assertNotNull(found);
        assertEquals(email, found.getEmail());
    }

    @Test
    public void findUserByEmail_returnsNullIfNotFound() {
        final String email = "invalid_email@example.com";

        when(query.stream()).thenReturn(Stream.empty());

        final User found = userRepository.findUserByEmail(email);
        assertNull(found);
    }

    @Test
    public void deleteUser_deletesUser() {
        final User user = new User();
        userRepository.deleteUser(user);
        verify(datastore, times(1)).delete(user);
    }

    @Test
    public void existsByEmail_checksExistence() {
        final String email = "exists@example.com";
        final User user = new User();
        user.setEmail(email);

        when(query.stream()).thenReturn(Stream.of(user));

        boolean exists = userRepository.existsByEmail(email);

        assertTrue(exists);
    }

    @Test
    public void existsByEmail_returnsFalseIfNotFound() {
        final String email = "invalid_email@example.com";

        when(query.stream()).thenReturn(Stream.empty());

        boolean exists = userRepository.existsByEmail(email);
        assertFalse(exists);
    }
}
