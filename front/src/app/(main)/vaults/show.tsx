import Api from '@/src/services/Api';
import ApiResponse from '@/src/types/ApiResponse';
import Vault from '@/src/types/Vault';
import FontAwesome6 from '@react-native-vector-icons/fontawesome6';
import { useLocalSearchParams } from 'expo-router';
import { useEffect, useState } from 'react';
import { Alert, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

const VaultPage = () => {
  const api = new Api();
  const { id } = useLocalSearchParams();
  const [vault, setVault] = useState<Vault>();

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
        <FontAwesome6 name={'key'} iconStyle="solid" size={30} color={'#fff'} />
      </View>
      <Text>ID: {id}</Text>
    </SafeAreaView>
  );
};

export default VaultPage;
