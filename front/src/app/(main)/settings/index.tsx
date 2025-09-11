import { Button, StyleSheet, Text, View } from 'react-native';
import React from 'react';
import { Link, router } from 'expo-router';
import AsyncStorage from '@react-native-async-storage/async-storage';

const SettingsPage = () => {
  
  const resetConfig = () => {
    const reset = () => {
      AsyncStorage.clear();
      router.push('/')
    }
    reset();
  }
  
  return (
    <View
      style={{
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <Button title='Apagar tudo' onPress={resetConfig} />
    </View>
  );
};

export default SettingsPage;

const styles = StyleSheet.create({});
