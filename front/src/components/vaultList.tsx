import { View, Text, ScrollView } from 'react-native';
import React from 'react';
import Vault from '../types/Vault';

const vaultList = (props: { vault: Vault[] }) => {
  return (
    <ScrollView showsVerticalScrollIndicator={false}>
      {props.vault.map((element) => (
        <Text key={element.id}>{element.username}</Text>
      ))}
    </ScrollView>
  );
};

export default vaultList;
