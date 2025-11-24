import { Image, Pressable, ScrollView, Text, View } from 'react-native';
import React, { useEffect, useState } from 'react';
import { SafeAreaView } from 'react-native-safe-area-context';
import WelcomeComponent from '@/src/components/welcome';
import SearchComponent from '@/src/components/search';
import Category from '../../../types/Category';

import api from '../../../services/api';
import FontAwesome6 from '@react-native-vector-icons/fontawesome6';
import AddButtonComponent from '@/src/components/addButton';

import NoCategories from '@/assets/images/svg/no-categories.svg';
import { router } from 'expo-router';

const CategoryPage = () => {
  const [categories, setCategories] = useState<Category[]>();
  const formatDate = (value: Date): string => {
    let [date, hours] = value.toString().split('T');
    date = date.split('-').reverse().join('/');
    hours = hours.split(':').slice(0, 2).join(':');
    return date + ' ' + hours;
  };

  useEffect(() => {
    api
      .get('/categories')
      .then((response) => setCategories(response.data.data))
      .catch((error) => console.error('erro na busca: ', error));
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

      <ScrollView
        showsVerticalScrollIndicator={false}
        contentContainerStyle={{ paddingTop: 20 }}
      >
        {categories && categories.length > 0 ? (
          categories.map((element) => (
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
                    name={'tag'}
                    iconStyle="solid"
                    size={15}
                    color={'#fff'}
                  />
                </View>
                <View style={{ gap: 6 }}>
                  <Text
                    style={{ color: '#333', fontSize: 16, fontWeight: '600' }}
                  >
                    {element.name}
                  </Text>
                  <Text style={{ color: '#888', fontSize: 12 }}>
                    {element.createdAt
                      ? formatDate(element.createdAt)
                      : '--/--/----'}
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
          <View style={{ alignItems: 'center', gap: 10 }}>
            <NoCategories width={320} height={320} />
            <View
              style={{
                width: '80%',
                gap: 12,
                paddingBottom: 40,
                alignItems: 'center',
              }}
            >
              <View style={{ alignItems: 'center', paddingBottom: 50 }}>
                <Text
                  style={{
                    fontWeight: '800',
                    fontSize: 20,
                    color: '#333',
                  }}
                >
                  Organize your passwords better
                </Text>
                <Text
                  style={{
                    color: '#999',
                    textAlign: 'center',
                  }}
                >
                  Add your first category and start organizing all your
                  passwords securely.
                </Text>
              </View>
              <Pressable
                style={({ pressed }) => [
                  {
                    backgroundColor: '#6c63ff',
                    width: '70%',
                    paddingVertical: 15,
                    borderRadius: 10,
                    opacity: pressed ? 0.9 : 1,
                  },
                ]}
                onPress={() => router.push('/categories/new')}
              >
                <Text
                  style={{
                    color: '#fff',
                    fontWeight: '800',
                    textAlign: 'center',
                  }}
                >
                  Add A Category
                </Text>
              </Pressable>
            </View>
          </View>
        )}
      </ScrollView>

      {categories && categories.length > 0 && (
        <View style={{ position: 'absolute', left: '94%', bottom: '2%' }}>
          <AddButtonComponent url={'/categories/new'} />
        </View>
      )}
    </SafeAreaView>
  );
};

export default CategoryPage;
