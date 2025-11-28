import { useEffect, useState } from 'react';

import { useRouter } from 'expo-router';
import AsyncStorage from '@react-native-async-storage/async-storage';

const IndexPage = () => {
  const router = useRouter();
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const checkFirstAccess = async () => {
      const hasSeenWelcome = await AsyncStorage.getItem('hasSeenWelcome');
      if (!hasSeenWelcome) {
        router.replace('/(auth)/welcome');
      } else {
        router.replace('/(auth)/login');
      }
      setLoading(false);
    };

    checkFirstAccess();
  }, []);

  if (loading) return null;
};

export default IndexPage;
