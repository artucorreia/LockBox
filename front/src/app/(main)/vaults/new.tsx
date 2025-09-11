import { View, Text, Pressable, TextInput, Alert } from 'react-native';
import React from 'react';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { router } from 'expo-router';
import api from '@/src/services/api';
import ApiResponse from '@/src/types/ApiResponse';

type FormData = {
  url: string;
  username: string;
  password: string;
  categoryId: string;
};

const NewVaultPage = () => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();

  const onSubmit: SubmitHandler<FormData> = (data) => {
    api
      .post<ApiResponse>('/vaults', data)
      .then((response) => {
        if (response.data.success) {
          Alert.alert('Success', 'category created successfully');
          router.push('/vaults');
          return;
        }

        Alert.alert('Error', 'error creating category');
        router.push('/vaults');
        return;
      })
      .catch((error) => console.log(error));
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
        onPress={() => router.push('/(main)/categories')}
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
        New Vault
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
              name="url"
              rules={{
                required: 'url filed is mandatory',
                maxLength: {
                  value: 255,
                  message: 'the url field must be 255 characters or less',
                },
              }}
              render={({ field: { onChange, value } }) => (
                <TextInput
                  style={{ width: '100%' }}
                  maxLength={20}
                  placeholder="Url"
                  value={value}
                  onChangeText={onChange}
                />
              )}
            />
          </View>
          {errors.url && (
            <Text style={{ color: '#FF2056' }}>{errors.url.message}</Text>
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
              name="username"
              rules={{
                required: 'username filed is mandatory',
                maxLength: {
                  value: 50,
                  message: 'username field must be 3 to 50 characters long',
                },
                minLength: {
                  value: 3,
                  message: 'username field must be 3 to 50 characters long',
                },
              }}
              render={({ field: { onChange, value } }) => (
                <TextInput
                  style={{ width: '100%' }}
                  maxLength={50}
                  placeholder="Username"
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
              name="password"
              rules={{
                required: 'password filed is mandatory',
                maxLength: {
                  value: 50,
                  message: 'password field must be 3 to 50 characters long',
                },
                minLength: {
                  value: 3,
                  message: 'password field must be 3 to 50 characters long',
                },
              }}
              render={({ field: { onChange, value } }) => (
                <TextInput
                  secureTextEntry={true}
                  style={{ width: '100%' }}
                  maxLength={50}
                  placeholder="Password"
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
              name="categoryId"
              rules={{
                required: 'category id filed is mandatory',
              }}
              render={({ field: { onChange, value } }) => (
                <TextInput
                  secureTextEntry={true}
                  style={{ width: '100%' }}
                  maxLength={5}
                  placeholder="Category Id"
                  value={value}
                  onChangeText={onChange}
                />
              )}
            />
          </View>
          {errors.categoryId && (
            <Text style={{ color: '#FF2056' }}>{errors.categoryId.message}</Text>
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

export default NewVaultPage;
