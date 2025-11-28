import { View, Text, TextInputComponent, TextInput } from 'react-native';
import React from 'react';

import FontAwesome6 from '@react-native-vector-icons/fontawesome6';

const SearchComponent = (props: {
  placeholder?: string;
  onInputChange: (value: string) => void;
}) => {
  return (
    <View
      style={{
        flexDirection: 'row',
        alignItems: 'center',
        gap: 8,
        backgroundColor: '#f9f9f9ff',
        paddingHorizontal: 12,
        paddingVertical: 4,
        borderRadius: 10,
      }}
    >
      <FontAwesome6
        name="magnifying-glass"
        iconStyle="solid"
        color={'#dcdcdc'}
        size={20}
      />
      <TextInput
        onChangeText={props.onInputChange}
        placeholder={props.placeholder || 'Search for passwords, categories'}
        style={{ flex: 1, color: '#333' }}
      />
    </View>
  );
};

export default SearchComponent;
