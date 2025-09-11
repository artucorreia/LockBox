import { View, Text, Button, Pressable, TextInput } from 'react-native';
import React from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { router } from 'expo-router';
import { useForm, Controller, SubmitHandler } from 'react-hook-form';

type FormData = {
  username: string;
  passwordKey: string;
};

const RegisterUserPage = () => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();

  const onSubmit: SubmitHandler<FormData> = (data) => {
    const saveData = async () => {
      AsyncStorage.setItem('hasSeenWelcome', 'true');
      AsyncStorage.setItem('user.name', data.username);
      AsyncStorage.setItem('user.passwordKey', data.passwordKey);
    };
    saveData();
    router.push('/home');
  };

  return (
    <View
      style={{
        flex: 1,
        backgroundColor: '#fff',
        alignItems: 'center',
        justifyContent: 'center',
      }}
    >
      <Pressable
        style={({ pressed }) => [
          {
            backgroundColor: '#6c63ff',
            paddingHorizontal: '5%',
            paddingVertical: 10,
            borderRadius: 10,
            opacity: pressed ? 0.9 : 1,
            position: 'absolute',
            top: 70,
            right: '5%',
          },
        ]}
        onPress={() => router.push('/(auth)/welcome')}
      >
        <Text
          style={{
            color: '#fff',
            fontWeight: '800',
          }}
        >
          Back
        </Text>
      </Pressable>

      <Text
        style={{
          color: '#333',
          fontWeight: '700',
          fontSize: 22,
          paddingBottom: 20,
        }}
      >
        Register
      </Text>

      <View
        style={{
          gap: 15,
          width: '50%',
        }}
      >
        <View>
          <View
            style={{
              borderWidth: 1,
              width: '100%',
              borderColor: '#e9e9e9',
              paddingHorizontal: 12,
              paddingVertical: 4,
              borderRadius: 12,
            }}
          >
            <Controller
              control={control}
              name="username"
              rules={{
                required: 'name filed is mandatory',
                maxLength: {
                  value: 20,
                  message: 'name field must be 3 to 20 characters long',
                },
                minLength: {
                  value: 3,
                  message: 'name field must be 3 to 20 characters long',
                },
              }}
              render={({ field: { onChange, value } }) => (
                <TextInput
                  style={{ width: '100%' }}
                  maxLength={20}
                  placeholder="Name"
                  value={value}
                  onChangeText={onChange}
                />
              )}
            />
          </View>
          {errors.username && (
            <Text style={{ color: '#FF2056' }}>{errors.username.message}</Text>
          )}
        </View>
        <View>
          <View
            style={{
              borderWidth: 1,
              width: '100%',
              borderColor: '#e9e9e9',
              paddingHorizontal: 12,
              paddingVertical: 4,
              borderRadius: 12,
            }}
          >
            <Controller
              control={control}
              name="passwordKey"
              rules={{
                required: 'password key filed is mandatory',
                maxLength: {
                  value: 20,
                  message: 'password key field must be 8 to 20 characters long',
                },
                minLength: {
                  value: 8,
                  message: 'password key field must be 8 to 20 characters long',
                },
              }}
              render={({ field: { onChange, value } }) => (
                <TextInput
                  secureTextEntry={true}
                  style={{ width: '100%' }}
                  placeholder="Password key"
                  value={value}
                  onChangeText={onChange}
                />
              )}
            />
          </View>
          {errors.passwordKey && (
            <Text style={{ color: '#FF2056' }}>
              {errors.passwordKey.message}
            </Text>
          )}
        </View>
        <Pressable
          style={({ pressed }) => [
            {
              backgroundColor: '#6c63ff',
              width: '100%',
              alignItems: 'center',
              paddingVertical: 10,
              borderRadius: 10,
              opacity: pressed ? 0.9 : 1,
            },
          ]}
          onPress={handleSubmit(onSubmit)}
        >
          <Text
            style={{
              color: '#fff',
              fontWeight: '800',
            }}
          >
            Save
          </Text>
        </Pressable>
      </View>
    </View>
  );
};

export default RegisterUserPage;
