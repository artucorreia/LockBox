import { Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import React, { useEffect, useState } from 'react';
import { Link } from 'expo-router';
import FontAwesome6 from '@react-native-vector-icons/fontawesome6';

// api
import api from '../../../services/api';

// types
import Category from '../../../types/Category';
import Vault from '../../../types/Vault';

// components
import WelcomeComponent from '../../../components/welcome';
import SearchComponent from '../../../components/search';
import CategoryCarouselComponent from '../../../components/categoryCarousel';

const HomePage = () => {
  const [categories, setCategories] = useState<Category[]>();
  const [vaults, setVaults] = useState<Vault[]>();

  useEffect(() => {
    api
      .get('/categories?size=4&direction=desc&pagination=true')
      .then((response) => setCategories(response.data.data))
      .catch((error) => console.error('categories error: ', error));
    api
      .get('/vaults?pagination=true&page=0&size=5&direction=asc')
      .then((response) => setVaults(response.data.data))
      .catch((error) => console.error('vaults error: ', error));
  }, []);

  return (
    <SafeAreaView
      edges={['top']}
      style={{
        flex: 1,
        backgroundColor: '#fff',
        paddingRight: 20,
        paddingLeft: 20,
      }}
    >
      <View style={{ paddingTop: 5, paddingBottom: 20 }}>
        <WelcomeComponent />
      </View>
      <SearchComponent />
      <View style={{ gap: 10, paddingVertical: 25 }}>
        <View
          style={{
            flexDirection: 'row',
            justifyContent: 'space-between',
            alignItems: 'center',
          }}
        >
          <Text style={{ fontWeight: 'bold', fontSize: 18, color: '#333' }}>
            My recent categories
          </Text>
          <Link href={'/categories'}>
            <Text style={{ color: '#999' }}>See all</Text>
          </Link>
        </View>
        {categories && categories.length > 0 ? (
          <CategoryCarouselComponent categories={categories} />
        ) : (
          <View>
            <Text>Nothing to see here...</Text>
          </View>
        )}
      </View>
      <View style={{ gap: 10, paddingVertical: 25 }}>
        <View
          style={{
            flexDirection: 'row',
            justifyContent: 'space-between',
            alignItems: 'center',
          }}
        >
          <Text style={{ fontWeight: 'bold', fontSize: 18, color: '#333' }}>
            My recent vaults
          </Text>
          <Link href={'/vaults'}>
            <Text style={{ color: '#999' }}>See all</Text>
          </Link>
        </View>
        {vaults && vaults.length > 0 ? (
          vaults.map((element: Vault) => (
            <View
              key={element.id}
              style={{
                width: '100%',
                height: 100,
                flexDirection: 'row',
                alignItems: 'center',
                justifyContent: 'space-between',
              }}
            >
              <View
                style={{
                  flexDirection: 'row',
                  alignItems: 'center',
                  justifyContent: 'flex-start',
                  gap: 5,
                }}
              >
                <View
                  style={{
                    width: 60,
                    height: 60,
                    borderRadius: 10,
                    alignItems: 'center',
                    justifyContent: 'center',
                    backgroundColor: '#6c63ff',
                  }}
                >
                  <FontAwesome6
                    name={'key'}
                    iconStyle="solid"
                    size={15}
                    color={'#fff'}
                  />
                </View>
                <View style={{ gap: 6 }}>
                  <Text
                    style={{ color: '#333', fontSize: 16, fontWeight: '600' }}
                  >
                    {element.username}
                  </Text>
                  <Text style={{ color: '#888', fontSize: 12 }}>
                    {element.url}
                  </Text>
                </View>
              </View>
              <FontAwesome6
                style={{ color: '#999' }}
                name="angle-right"
                iconStyle="solid"
                size={22}
              />
            </View>
          ))
        ) : (
          <Text>Nothing to see here...</Text>
        )}
      </View>
    </SafeAreaView>
  );
};

export default HomePage;
