import React from 'react';
import Api from '@/src/services/Api';
import ApiResponse from '@/src/types/ApiResponse';
import { View, Text, Pressable, TextInput, Alert } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useForm, Controller, SubmitHandler } from 'react-hook-form';
import { router } from 'expo-router';
import Token from '@/src/types/Token';

type FormData = {
  email: string;
  password: string;
};

const LoginPage = () => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();
  const api = new Api();

  const onSubmit: SubmitHandler<FormData> = (data) => {
    const process = async (data: FormData) => {
      try {
        const response = await api.post<ApiResponse<Token>>(
          '/auth/login',
          data
        );
        const token: string = response.data?.token || '';
        const username: string = response.data?.userName || '';
        AsyncStorage.setItem('user.token', token);
        AsyncStorage.setItem('user.name', username);
        router.push('/(main)/home');
      } catch (error) {
        Alert.alert('Error', 'Login error');
      }
    };

    process(data);
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
      <Text
        style={{
          color: '#333',
          fontWeight: '700',
          fontSize: 22,
          paddingBottom: 20,
        }}
      >
        Login
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
              name="email"
              rules={{
                required: 'email filed is mandatory',
                maxLength: {
                  value: 60,
                  message: 'email field must be 2 to 60 characters long',
                },
                minLength: {
                  value: 2,
                  message: 'email field must be 2 to 60 characters long',
                },
              }}
              render={({ field: { onChange, value } }) => (
                <TextInput
                  style={{ width: '100%' }}
                  maxLength={60}
                  placeholder="E-mail"
                  value={value}
                  onChangeText={onChange}
                />
              )}
            />
          </View>
          {errors.email && (
            <Text style={{ color: '#FF2056' }}>{errors.email.message}</Text>
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
              name="password"
              rules={{
                required: 'password filed is mandatory',
                maxLength: {
                  value: 50,
                  message: 'password field must be 8 to 50 characters long',
                },
                minLength: {
                  value: 8,
                  message: 'password field must be 8 to 50 characters long',
                },
              }}
              render={({ field: { onChange, value } }) => (
                <TextInput
                  secureTextEntry={true}
                  style={{ width: '100%' }}
                  placeholder="Password"
                  maxLength={50}
                  value={value}
                  onChangeText={onChange}
                />
              )}
            />
          </View>
          {errors.password && (
            <Text style={{ color: '#FF2056' }}>{errors.password.message}</Text>
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
            Submit
          </Text>
        </Pressable>
        <View>
          <Text style={{ opacity: 0.7 }}>Don't have account yet?</Text>
          <Pressable
            onPress={() => router.push('/(auth)/welcome/registerUser')}
          >
            <Text style={{ color: '#6c63ff', fontWeight: 800 }}>
              Sing up here
            </Text>
          </Pressable>
        </View>
      </View>
    </View>
  );
};

export default LoginPage;
