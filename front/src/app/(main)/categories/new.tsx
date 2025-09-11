import { View, Text, Pressable, TextInput, Alert } from 'react-native';
import React from 'react';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { router } from 'expo-router';
import api from '@/src/services/api';
import ApiResponse from '@/src/types/ApiResponse';

type FormData = {
  name: string;
};

const NewCategoryPage = () => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();

  const onSubmit: SubmitHandler<FormData> = (data) => {
    api
      .post<ApiResponse>('/categories', data)
      .then((response) => {
        if (response.data.success) {
          Alert.alert('Success', 'category created successfully');
          router.push('/(main)/categories');
          return;
        }

        Alert.alert('Error', 'error creating category');
        router.push('/(main)/categories');
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
        New Category
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
              name="name"
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
          {errors.name && (
            <Text style={{ color: '#FF2056' }}>{errors.name.message}</Text>
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

export default NewCategoryPage;
