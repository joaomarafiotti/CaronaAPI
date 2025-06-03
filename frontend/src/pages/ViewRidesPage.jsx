import React, { useState, useEffect, useMemo } from 'react';
import { useAuth } from '../context/AuthContext';
import DriverRide from '../components/DriverRide';
import { getDriverRides, cancelRideById } from '../services/rideService';
import { Heading, HStack, Spinner, Alert, VStack } from '@chakra-ui/react';

const ViewRidesPage = () => {
  const [rides, setRides] = useState([]);
  const { userToken } = useAuth();
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchRides = async () => {
      try {
        const response = await getDriverRides(userToken);
        setRides(response.data);
      } catch (err) {
        setError('Erro ao buscar caronas');
      } finally {
        setLoading(false);
      }
    };

    fetchRides();
  }, [userToken]);

  const onCancelRide = (rideId) => {
    try {
      cancelRideById(rideId, userToken);
     
      setRides((prevRides) =>
        prevRides.map((r) =>
          r.uuid === rideId ? { ...r, status: 'CANCELLED' } : r
        )
      );
    
    } catch (err) {
      setError('Erro ao cancelar carona');
    }
  };

  const groupedRides = useMemo(() => {
    const groups = {
      notStarted: [],
      inProgress: [],
      finished: [],
    };

    rides.forEach((ride) => {
      if (ride.status === "WAITING" || ride.status === "FULL") {
        groups.notStarted.push(ride);
      } else if (ride.status === "STARTED") {
        groups.inProgress.push(ride);
      } else if (ride.status === "CANCELLED" || ride.status === "FINISHED") {
        groups.finished.push(ride);
      }
    });

    return groups;
  }, [rides]);

  const renderRideGroup = (title, rides) => {
    if (rides.length === 0) return null;

    return (
      <VStack align="start" spacing={4}>
        <Heading size="md">{title}</Heading>
        <HStack align="start" spacing={4} wrap="wrap">
          {rides.map((ride) => (
            <DriverRide key={ride.uuid} ride={ride} onCancel={onCancelRide} />
          ))}
        </HStack>
      </VStack>
    );
  };

  if (loading) {
    return <Spinner size="lg" />;
  }

  if (error) {
    return (
      <Alert status="error">
        {error}
      </Alert>
    );
  }

  return (
    <VStack align="start" spacing={8} p={4}>
      {renderRideGroup("Caronas não iniciadas", groupedRides.notStarted)}
      {renderRideGroup("Em andamento", groupedRides.inProgress)}
      {renderRideGroup("Caronas encerradas", groupedRides.finished)}
    </VStack>
  );
};

export default ViewRidesPage;
