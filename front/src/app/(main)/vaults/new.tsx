import { View, Text, Pressable, TextInput, Alert } from 'react-native';
import React, { useEffect, useState } from 'react';
import { Controller, SubmitHandler, useForm } from 'react-hook-form';
import { Picker } from '@react-native-picker/picker';
import { router } from 'expo-router';
import Api from '@/src/services/Api';
import ApiResponse from '@/src/types/ApiResponse';
import Category from '@/src/types/Category';

type FormData = {
  url: string;
  username: string;
  password: string;
  categoryId: string;
};

const NewVaultPage = () => {
  const api = new Api();
  const [categories, setCategories] = useState<Category[]>();
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();

  useEffect(() => {
    const process = async () => {
      try {
        const categoriesResponse: ApiResponse<Category[]> = await api.get(
          '/v1/categories',
        );
        setCategories(categoriesResponse.data);
      } catch (error) {
        Alert.alert('Error', 'An unexpected error occurred');
      }
    };

    process();
    console.log(categories);
  }, []);

  const onSubmit: SubmitHandler<FormData> = (data) => {
    const process = async (data: FormData) => {
      try {
        await api.post<ApiResponse<null>>('/v1/vaults', data);
        Alert.alert('Success', 'Vault created successfully');
      } catch (error) {
        Alert.alert('Error', 'error creating vault');
      } finally {
        router.push('/(main)/vaults');
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
        onPress={() => router.push('/(main)/vaults')}
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
        <View
          style={{
            borderWidth: 1,
            width: '100%',
            borderColor: '#e9e9e9',
            paddingHorizontal: 12,
            borderRadius: 12,
          }}
        >
          <Controller
            control={control}
            name="categoryId"
            rules={{
              required: 'category field is mandatory',
            }}
            render={({ field: { onChange, value } }) => (
              <Picker
                selectedValue={value}
                onValueChange={(val) => onChange(val)}
                style={{ width: '100%' }}
              >
                <Picker.Item
                  label="Category"
                  style={{ color: '#999' }}
                  value={null}
                  enabled={false}
                />

                {categories?.map((category) => (
                  <Picker.Item
                    key={category.id}
                    label={category.name}
                    value={category.id}
                  />
                ))}
              </Picker>
            )}
          />
        </View>

        {errors.categoryId && (
          <Text style={{ color: '#FF2056' }}>{errors.categoryId.message}</Text>
        )}

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
