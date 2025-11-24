import { View, Text, ScrollView } from 'react-native';
import React from 'react';
import FontAwesome6 from '@react-native-vector-icons/fontawesome6';
import Category from '../types/Category';

const colors: string[] = [
  '#CA3500',
  '#007A55',
  '#155DFC',
  '#E17100',
  '#314158',
  '#FF2056',
  '#7F22FE',
  '#292524',
];

const CategoryCarousel = (props: { categories: Category[] }) => {
  const getRandomColor = ():string => {
    const index: number = Math.floor(Math.random() * colors.length);
    return colors[index];
  };

  return (
    <ScrollView
      horizontal
      showsHorizontalScrollIndicator={false}
      contentContainerStyle={{ gap: 20 }}
    >
      {props.categories.map((element) => (
        <View
          key={element.id}
          style={{
            width: 100,
            height: 100,
            borderRadius: 10,
            gap: 5,
            borderColor: '#F5F5F5',
            borderWidth: 2,
            alignItems: 'center',
            justifyContent: 'center',
          }}
        >
          <View
            style={{
              width: 50,
              height: 50,
              borderRadius: '100%',
              alignItems: 'center',
              justifyContent: 'center',
              backgroundColor: getRandomColor(),
            }}
          >
            <FontAwesome6
              name={'tag'}
              iconStyle="solid"
              size={15}
              color={'#fff'}
            />
          </View>
          <Text style={{ color: '#333' }}>{element.name}</Text>
        </View>
      ))}
    </ScrollView>
  );
};

export default CategoryCarousel;
