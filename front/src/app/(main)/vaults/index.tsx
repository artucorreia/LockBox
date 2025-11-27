import { Alert, Pressable, ScrollView, Text, View } from 'react-native';
import React, { useEffect, useState } from 'react';
import { router } from 'expo-router';
import { SafeAreaView } from 'react-native-safe-area-context';
import FontAwesome6 from '@react-native-vector-icons/fontawesome6';

// api
import Api from '@/src/services/Api';

// svgs
import NoVaults from '@/assets/images/svg/no-vaults.svg';

// types
import Category from '@/src/types/Category';

// components
import AddButtonComponent from '@/src/components/addButton';
import WelcomeComponent from '@/src/components/welcome';
import SearchComponent from '@/src/components/search';
import ApiResponse from '@/src/types/ApiResponse';
import Vault from '@/src/types/Vault';

const VaultPage = () => {
  const api = new Api();
  const [vaultsGrouped, setVaultsGrouped] = useState<Category[]>();

  useEffect(() => {
    const process = async () => {
      try {
        const vaultsResponse: ApiResponse<Vault[]> = await api.get(
          '/v1/vaults/grouped',
        );
        setVaultsGrouped(vaultsResponse.data);
      } catch (error) {
        Alert.alert('Error', 'An unexpected error occurred');
      }
    };

    process();
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
      <SearchComponent placeholder={'Search for vaults'} />

      <ScrollView
        showsVerticalScrollIndicator={false}
        contentContainerStyle={{ paddingTop: 20 }}
      >
        {vaultsGrouped && vaultsGrouped.length > 0 ? (
          vaultsGrouped.map((element) => (
            <View
              key={element.id}
              style={{
                width: '100%',
              }}
            >
              <Text style={{ color: '#333', fontSize: 18, fontWeight: 'bold' }}>
                {element.name}
              </Text>
              {element.vaults && element.vaults.length > 0 ? (
                element.vaults.map((element) => (
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
                          style={{
                            color: '#333',
                            fontSize: 16,
                            fontWeight: '600',
                          }}
                        >
                          {element.username}
                        </Text>
                        <Text style={{ color: '#888', fontSize: 12 }}>
                          {element.password}
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
                <Text style={{ paddingVertical: 10 }}>No vaults...</Text>
              )}
            </View>
          ))
        ) : (
          <View style={{ alignItems: 'center', gap: 10 }}>
            <NoVaults width={320} height={320} />
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
                  Store your passwords better
                </Text>
                <Text
                  style={{
                    color: '#999',
                    textAlign: 'center',
                  }}
                >
                  Add your passwords and never forget them again.
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
                onPress={() => router.push('/vaults/new')}
              >
                <Text
                  style={{
                    color: '#fff',
                    fontWeight: '800',
                    textAlign: 'center',
                  }}
                >
                  Add A Vault
                </Text>
              </Pressable>
            </View>
          </View>
        )}
      </ScrollView>

      {vaultsGrouped && vaultsGrouped.length > 0 && (
        <View style={{ position: 'absolute', left: '94%', bottom: '2%' }}>
          <AddButtonComponent url={'/vaults/new'} />
        </View>
      )}
    </SafeAreaView>
  );
};

export default VaultPage;
