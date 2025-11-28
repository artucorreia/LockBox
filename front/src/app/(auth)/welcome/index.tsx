import { Pressable, Text, View } from 'react-native';
import React, { useEffect } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { router } from 'expo-router';

import Welcome from '../../../../assets/images/svg/welcome.svg';

const WelcomePage = () => {
  useEffect(() => {
    AsyncStorage.setItem('hasSeenWelcome', 'true');
  }, []);

  return (
    <View
      style={{
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <Welcome width={350} height={350} />
      <View style={{ width: '80%', gap: 12, paddingBottom: 40 }}>
        <Text
          style={{
            color: '#333',
            fontSize: 32,
            fontWeight: '800',
            textAlign: 'center',
          }}
        >
          Never Forget a Password Anymore
        </Text>
        <Text
          style={{
            color: '#999',
            fontSize: 16,
            textAlign: 'center',
          }}
        >
          Lorem ipsum, dolor sit amet consectetur adipisicing elit, Blanditiis.
        </Text>
      </View>
      <Pressable
        style={({ pressed }) => [
          {
            backgroundColor: '#6c63ff',
            paddingHorizontal: '25%',
            paddingVertical: 18,
            borderRadius: 10,
            opacity: pressed ? 0.9 : 1,
          },
        ]}
        onPress={() => router.push('/(auth)/login')}
      >
        <Text
          style={{
            color: '#fff',
            fontWeight: '800',
          }}
        >
          Get Started
        </Text>
      </Pressable>
    </View>
  );
};

export default WelcomePage;
