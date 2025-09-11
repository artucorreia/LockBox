import { Image, Text, View } from 'react-native';
import React, { useEffect, useState } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';

import { avatars } from '@/assets/images';

const data = new Map();
data.set('1', avatars.avatar1);
data.set('2', avatars.avatar2);
data.set('3', avatars.avatar3);
data.set('4', avatars.avatar4);
data.set('5', avatars.avatar5);
data.set('6', avatars.avatar6);

const WelcomeComponent = () => {
  const [userName, setUserName] = useState<string | null>(null);
  const [avatarId, setAvatarId] = useState<string | null>(null);

  useEffect(() => {
    const loadData = async () => {
      const name = await AsyncStorage.getItem('user.name');
      const avatar = await AsyncStorage.getItem('avatar.id');
      setUserName(name);
      setAvatarId(avatar);
    };

    loadData();
  }, []);

  return (
    <View
      style={{
        flexDirection: 'row',
        alignItems: 'flex-end',
        justifyContent: 'space-between',
      }}
    >
      <View>
        <Text style={{ color: '#333' }}>Welcome back</Text>
        <Text style={{ color: '#333', fontWeight: 'bold', fontSize: 24 }}>
          {userName}
        </Text>
      </View>
      <View
        style={{
          backgroundColor: '#6c63ff',
          width: 40,
          height: 40,
          justifyContent: 'flex-end',
          alignItems: 'center',
          borderRadius: 10,
        }}
      >
        <Image style={{ width: 30, height: 30 }} source={data.get(avatarId)} />
      </View>
    </View>
  );
};

export default WelcomeComponent;
