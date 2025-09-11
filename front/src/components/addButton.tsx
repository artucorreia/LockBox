import { Pressable } from 'react-native';
import React from 'react';
import FontAwesome6 from '@react-native-vector-icons/fontawesome6';
import { ExternalPathString, Href, RelativePathString, router } from 'expo-router';

const AddButtonComponent = (props: { url: Href }) => {
  return (
    <Pressable
      style={({ pressed }) => [
        {
          backgroundColor: '#6c63ff',
          width: 50,
          height: 50,
          borderRadius: '100%',
          alignItems: 'center',
          justifyContent: 'center',
          opacity: pressed ? 0.9 : 1,
        },
      ]}
      onPress={() => router.push(props.url)}
    >
      <FontAwesome6 name="plus" iconStyle="solid" size={20} color={'#fff'} />
    </Pressable>
  );
};

export default AddButtonComponent;
