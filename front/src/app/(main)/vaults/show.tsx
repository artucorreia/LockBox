import Api from '@/src/services/Api';
import ApiResponse from '@/src/types/ApiResponse';
import Vault from '@/src/types/Vault';
import FontAwesome6 from '@react-native-vector-icons/fontawesome6';
import { router, useLocalSearchParams } from 'expo-router';
import { useEffect, useState } from 'react';
import { Alert, Linking, Pressable, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import * as Clipboard from 'expo-clipboard';

enum Field {
  URL = 'url',
  USERNAME = 'username',
  PASSWORD = 'password',
  NONE = 'none',
}

const VaultPage = () => {
  const api = new Api();
  const { id } = useLocalSearchParams();
  const [vault, setVault] = useState<Vault>();
  const [copiedField, setCopiedField] = useState<Field>(Field.NONE);
  const [isVisible, setIsVisible] = useState<boolean>(false);

  const formatDate = (value?: Date): string => {
    if (!value) return '---';
    let [date, hours] = value.toString().split('T');
    date = date.split('-').reverse().join('/');
    hours = hours.split(':').slice(0, 2).join(':');
    return date + ' ' + hours;
  };

  useEffect(() => {
    const process = async (id: string | string[]) => {
      try {
        const response = await api.get<ApiResponse<Vault>>(`v1/vaults/${id}`);
        setVault(response.data);
      } catch (error) {
        Alert.alert('Error', 'An unexpected error occurred');
      } finally {
        console.log(vault);
      }
    };

    process(id || '');
  }, []);

  const handlePress = async (url?: string) => {
    if (!url) {
      Alert.alert('Erro', `The link could not be opened.`);
      return;
    }
    const supported = await Linking.canOpenURL(url);

    if (!supported) {
      Alert.alert('Erro', `The link could not be opened.`);
      return;
    }
    await Linking.openURL(url);
  };

  const copyToClipboard = async (field: Field, text?: string) => {
    if (!text) {
      Alert.alert('Error', 'Error when copying field');
      return;
    }
    await Clipboard.setStringAsync(text);
    setCopiedField(field);
    setTimeout(() => setCopiedField(Field.NONE), 2000);
  };

  const toggleVisibility = () => setIsVisible(!isVisible);

  const deleteVault = async (id?: number) => {
    if (!id) {
      Alert.alert('Error', 'An unexpected error occurred');
      return;
    }
    try {
      await api.delete<ApiResponse<null>>(`/v1/vaults/${id}`);
      Alert.alert('Success', 'Vault delete successfully');
    } catch (error) {
      Alert.alert('Error', 'An unexpected error occurred');
    } finally {
      router.push('/(main)/vaults');
    }
  };

  return (
    <SafeAreaView
      edges={['top']}
      style={{
        flex: 1,
        backgroundColor: '#fff',
        paddingRight: 20,
        paddingLeft: 20,
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

      <View
        style={{
          gap: 20,
        }}
      >
        <View
          style={{
            flexDirection: 'row-reverse',
            alignItems: 'center',
            justifyContent: 'space-between',
            // gap: '10',
          }}
        >
          <View
            style={{
              width: 70,
              height: 70,
              borderRadius: 10,
              alignItems: 'center',
              justifyContent: 'center',
              backgroundColor: '#6c63ff',
            }}
          >
            <FontAwesome6
              name={'key'}
              iconStyle="solid"
              size={30}
              color={'#fff'}
            />
          </View>
          <Text style={{ fontWeight: 800, fontSize: 20 }}>
            {vault?.username}
          </Text>
        </View>
        <View
          style={{
            flexDirection: 'row',
            backgroundColor: '#303030',
            alignItems: 'center',
            paddingInline: 10,
            paddingVertical: 5,
            gap: 5,
            width: '25%',
            justifyContent: 'center',
            borderRadius: 20,
          }}
        >
          <FontAwesome6
            name={'tag'}
            iconStyle="solid"
            size={15}
            color={'#fff'}
          />
          <Text style={{ color: '#fff' }}>{vault?.category?.name}</Text>
        </View>

        <View>
          <Text style={{ color: '#0000008e' }}>WEBSITE URL</Text>
          <View
            style={{
              flexDirection: 'row',
              justifyContent: 'space-between',
            }}
          >
            <Pressable onPress={() => handlePress(vault?.url)}>
              <Text style={{ color: '#4285F4' }}>{vault?.url}</Text>
            </Pressable>
            <FontAwesome6
              onPress={() => copyToClipboard(Field.URL, vault?.url)}
              name={'copy'}
              iconStyle="solid"
              size={15}
              color={'#00000070'}
            />
          </View>
        </View>
        <View>
          <Text style={{ color: '#0000008e' }}>USERNAME</Text>
          <View
            style={{
              flexDirection: 'row',
              justifyContent: 'space-between',
            }}
          >
            <Text>{vault?.username}</Text>
            <FontAwesome6
              onPress={() => copyToClipboard(Field.USERNAME, vault?.username)}
              name={'copy'}
              iconStyle="solid"
              size={15}
              color={'#00000070'}
            />
          </View>
        </View>
        <View>
          <Text style={{ color: '#0000008e' }}>PASSWORD</Text>
          <View
            style={{
              flexDirection: 'row',
              justifyContent: 'space-between',
            }}
          >
            <Text>{isVisible ? vault?.password : '********'}</Text>
            <View style={{ flexDirection: 'row', gap: 10 }}>
              <FontAwesome6
                onPress={toggleVisibility}
                name={'eye'}
                iconStyle="solid"
                size={15}
                color={'#00000070'}
              />
              <FontAwesome6
                onPress={() => copyToClipboard(Field.PASSWORD, vault?.password)}
                name={'copy'}
                iconStyle="solid"
                size={15}
                color={'#00000070'}
              />
            </View>
          </View>
        </View>

        <View style={{ paddingTop: 10 }}>
          <View style={{ flexDirection: 'row', gap: 5, alignItems: 'center' }}>
            <FontAwesome6
              name={'clock'}
              iconStyle="solid"
              size={12}
              color={'#00000070'}
            />
            <Text style={{ fontSize: 12, color: '#00000070' }}>
              Created: {formatDate(vault?.createdAt)}
            </Text>
          </View>
          <View style={{ flexDirection: 'row', gap: 5, alignItems: 'center' }}>
            <FontAwesome6
              name={'clock'}
              iconStyle="solid"
              size={12}
              color={'#00000070'}
            />
            <Text style={{ fontSize: 12, color: '#00000070' }}>
              Updated: {formatDate(vault?.updatedAt)}
            </Text>
          </View>
        </View>
      </View>

      <Pressable
        onPress={() => deleteVault(vault?.id)}
        style={{
          flexDirection: 'row',
          marginTop: '10%',
          // backgroundColor: '#FF2056',
          paddingVertical: 10,
          borderRadius: 5,
          justifyContent: 'center',
          alignItems: 'center',
          gap: 10,
        }}
      >
        <FontAwesome6
          name={'trash'}
          iconStyle="solid"
          size={15}
          color={'#FF2056'}
        />
        <Text style={{ color: '#FF2056' }}>Delete Vault</Text>
      </Pressable>
    </SafeAreaView>
  );
};

export default VaultPage;
