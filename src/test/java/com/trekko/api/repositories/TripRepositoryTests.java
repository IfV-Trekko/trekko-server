package com.trekko.api.repositories;

import com.trekko.api.models.Trip;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TripRepositoryTest {

    @Mock
    private Datastore datastore;
    @Mock
    private Query<Trip> query;

    @InjectMocks
    private TripRepository tripRepository;

    @Captor
    private ArgumentCaptor<Trip> tripArgumentCaptor;

    private final User mockUser = new User();
    private final ObjectId mockUserId = new ObjectId();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.mockUser.setId(this.mockUserId);

        when(datastore.find(Trip.class)).thenReturn(query);
    }

    @Test
    void saveTrip_savesTripSuccessfully() {
        final Trip trip = new Trip();
        tripRepository.saveTrip(trip);
        verify(datastore, times(1)).save(tripArgumentCaptor.capture());
        assertEquals(trip, tripArgumentCaptor.getValue());
    }

    @Test
    void findTripById_findsTrip() {
        final ObjectId id = new ObjectId();
        final Trip trip = new Trip();
        trip.setId(id);

        when(this.query.stream()).thenReturn(Stream.of(trip));

        final Trip found = tripRepository.findTripById(id);

        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    void deleteAllTripsByUser_deletesAllUserTrips() {
        final User user = new User();
        user.setId(new ObjectId());

        final Trip trip1 = new Trip();
        trip1.setUser(user);
        final Trip trip2 = new Trip();
        trip2.setUser(user);

        when(this.query.stream()).thenReturn(Stream.of(trip1, trip2));

        tripRepository.deleteAllTripsByUser(user);

        verify(datastore, times(2)).delete(any(Trip.class));
    }

    @Test
    void getTripsByUser_returnsUserTrips() {
        final Trip trip = new Trip();
        trip.setUser(this.mockUser);

        when(this.query.stream()).thenReturn(Stream.of(trip));

        List<Trip> trips = tripRepository.getTripsByUser(this.mockUser);

        assertFalse(trips.isEmpty());
        assertEquals(this.mockUser.getId(), trips.get(0).getUser().getId());
    }

    @Test
    void existsTripByUid_checksExistence() {
        final String uid = "uniqueId";
        final Trip trip = new Trip();
        trip.setUid(uid);
        trip.setUser(this.mockUser);

        when(this.query.stream()).thenReturn(Arrays.asList(trip).stream());

        boolean exists = tripRepository.existsTripByUid(uid, this.mockUser);
        assertTrue(exists);
    }

    @Test
    void getFirst_returnsFirstTrip() {
        final Trip trip = new Trip();
        when(datastore.find(Trip.class).first()).thenReturn(trip);

        final Trip first = tripRepository.getFirst();
        assertNotNull(first);
    }

    @Test
    void getCount_returnsCorrectCount() {
        final long expectedCount = 5L;
        when(datastore.find(Trip.class).count()).thenReturn(expectedCount);

        final long count = tripRepository.getCount();
        assertEquals(expectedCount, count);
    }
}
