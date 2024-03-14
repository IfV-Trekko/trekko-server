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
import static org.junit.jupiter.api.Assertions.assertNull;
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
    public void saveTrip_savesTripSuccessfully() {
        final Trip trip = new Trip();
        tripRepository.saveTrip(trip);
        verify(datastore, times(1)).save(tripArgumentCaptor.capture());
        assertEquals(trip, tripArgumentCaptor.getValue());
    }

    @Test
    public void findTripById_findsTrip() {
        final ObjectId id = new ObjectId();
        final Trip trip = new Trip();
        trip.setId(id);

        when(this.query.stream()).thenReturn(Stream.of(trip));

        final Trip found = tripRepository.findTripById(id);

        assertNotNull(found);
        assertEquals(id, found.getId());
    }

    @Test
    public void findTripById_returnsNullIfNotFound() {
        final ObjectId id = new ObjectId();
        when(this.query.stream()).thenReturn(Stream.empty());

        final Trip found = tripRepository.findTripById(id);

        assertNull(found);
    }

    @Test
    public void findTripByUid_findsTrip() {
        final String uid = "uniqueId";
        final Trip trip = new Trip();
        trip.setUid(uid);
        trip.setUser(this.mockUser);

        when(this.query.stream()).thenReturn(Stream.of(trip));

        final Trip found = tripRepository.findTripByUid(uid, this.mockUser);

        assertNotNull(found);
        assertEquals(uid, found.getUid());
    }

    @Test
    public void findTripByUid_returnsNullIfNotFound() {
        final String uid = "uniqueId";
        when(this.query.stream()).thenReturn(Stream.empty());

        final Trip found = tripRepository.findTripByUid(uid, this.mockUser);

        assertNull(found);
    }

    @Test
    public void deleteAllTripsByUser_deletesAllUserTrips() {
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
    public void getTripsByUser_returnsUserTrips() {
        final Trip trip = new Trip();
        trip.setUser(this.mockUser);

        when(this.query.stream()).thenReturn(Stream.of(trip));

        List<Trip> trips = tripRepository.getTripsByUser(this.mockUser);

        assertFalse(trips.isEmpty());
        assertEquals(this.mockUser.getId(), trips.get(0).getUser().getId());
    }

    @Test
    public void existsTripByUid_checksExistence() {
        final String uid = "uniqueId";
        final Trip trip = new Trip();
        trip.setUid(uid);
        trip.setUser(this.mockUser);

        when(this.query.stream()).thenReturn(Arrays.asList(trip).stream());

        boolean exists = tripRepository.existsTripByUid(uid, this.mockUser);
        assertTrue(exists);
    }

    @Test
    public void existsTripByUid_returnsFalseIfNotFound() {
        final String uid = "uniqueId";

        when(this.query.stream()).thenReturn(Stream.empty());

        boolean exists = tripRepository.existsTripByUid(uid, this.mockUser);
        assertFalse(exists);
    }

    @Test
    public void existTripsWithUids_checksExistence() {
        final String uid1 = "uniqueId1";
        final String uid2 = "uniqueId2";
        final Trip trip1 = new Trip();
        trip1.setUid(uid1);
        trip1.setUser(this.mockUser);
        final Trip trip2 = new Trip();
        trip2.setUid(uid2);
        trip2.setUser(this.mockUser);

        when(this.query.stream()).thenReturn(Arrays.asList(trip1, trip2).stream());

        boolean exists = tripRepository.existTripsWithUids(Arrays.asList(uid1, uid2), this.mockUser);
        assertTrue(exists);
    }

    @Test
    public void existTripsWithUids_returnsFalseIfNotFound() {
        final String uid1 = "uniqueId1";
        final String uid2 = "uniqueId2";

        when(this.query.stream()).thenReturn(Stream.empty());

        boolean exists = tripRepository.existTripsWithUids(Arrays.asList(uid1, uid2), this.mockUser);
        assertFalse(exists);
    }

    @Test
    public void deleteTrip_deletesTrip() {
        final Trip trip = new Trip();
        tripRepository.deleteTrip(trip);
        verify(datastore, times(1)).delete(trip);
    }

    @Test
    public void getFirst_returnsFirstTrip() {
        final Trip trip = new Trip();
        when(datastore.find(Trip.class).first()).thenReturn(trip);

        final Trip first = tripRepository.getFirst();
        assertNotNull(first);
    }

    @Test
    public void getCount_returnsCorrectCount() {
        final long expectedCount = 5L;
        when(datastore.find(Trip.class).count()).thenReturn(expectedCount);

        final long count = tripRepository.getCount();
        assertEquals(expectedCount, count);
    }
}
