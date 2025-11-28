import { Text, View } from 'react-native';
import React, { useEffect } from 'react';
import { Href, router, Stack, usePathname } from 'expo-router';
import {
  FontAwesome6,
  FontAwesome6SolidIconName,
} from '@react-native-vector-icons/fontawesome6';

const routes: {
  id: number;
  path: Href;
  icon: FontAwesome6SolidIconName;
  name: string;
}[] = [
  { id: 1, path: '/home', icon: 'house', name: 'Home' },
  { id: 2, path: '/vaults', icon: 'lock', name: 'Vault' },
  { id: 3, path: '/categories', icon: 'tag', name: 'Category' },
  { id: 4, path: '/settings', icon: 'gear', name: 'Settings' },
];

const RootLayout = () => {
  const pathName = usePathname();

  return (
    <View style={{ flex: 1 }}>
      <Stack>
        <Stack.Screen
          name="home/index"
          options={{ title: 'Home', headerShown: false }}
        />

        {/* Vaults */}
        <Stack.Screen
          name="vaults/index"
          options={{ title: 'Vaults', headerShown: false }}
        />
        <Stack.Screen
          name="vaults/new"
          options={{ title: 'New Vault', headerShown: false }}
        />
        <Stack.Screen
          name="vaults/show"
          options={{ title: 'Vault', headerShown: false }}
        />

        {/* Categories */}
        <Stack.Screen
          name="categories/index"
          options={{ title: 'Categories', headerShown: false }}
        />
        <Stack.Screen
          name="categories/new"
          options={{ title: 'New Category', headerShown: false }}
        />

        {/* Settings */}
        <Stack.Screen
          name="settings/index"
          options={{ title: 'Settings', headerShown: false }}
        />
      </Stack>

      <View
        style={{
          flexDirection: 'row',
          justifyContent: 'space-between',
          paddingVertical: 20,
          paddingInline: 20,
          boxShadow: '5px 5px 10px #00000080',
        }}
      >
        {routes.map((element) => (
          <View
            key={element.id}
            style={{ alignItems: 'center', width: 60 }}
            onTouchStart={() => router.push(element.path)}
          >
            <FontAwesome6
              name={element.icon}
              iconStyle="solid"
              size={20}
              style={{
                color: pathName.startsWith(element.path.toString())
                  ? '#6c63ff'
                  : '#333',
              }}
            />
            <Text
              style={{
                color: pathName.startsWith(element.path.toString())
                  ? '#6c63ff'
                  : '#333',
              }}
            >
              {element.name}
            </Text>
          </View>
        ))}
      </View>
    </View>
  );
};

export default RootLayout;
