# LockBox

## 📋 Sobre o Projeto

Projeto desenvolvido para facilitar o dia a dia, agrupando e gerenciando diversas senhas e acessos, focado em praticidade e otimização de tempo

## 🛠️ Tecnologias

### Backend (`./back/`)
- Java 21+
- Spring Boot
- Spring Data JPA
- Spring Security
- Maven
- H2 (development)
- PostgreSQL (production)

### Frontend (`./front/`)
- React Native
- Expo
- React Navigation
- Axios
- AsyncStorage
- Alert

## 📁 Estrutura do Projeto

```
.
├── back/           # Backend da aplicação
└── front/          # Frontend da aplicação
```

## 🚀 Como Executar

### Pré-requisitos

- Docker
- Docker compose
- Node 24+
- npm
- Expo Go (dispositivo móvel)

### Backend Dev Mode

```bash
docker compose up -d
```

O servidor estará rodando em `http://localhost:8080`

### Backend Prod Mode

```bash
docker compose -f docker-compose.prod.yaml up -d
```

O servidor estará rodando em `http://localhost:8080`

O postgres estará aceitando conexões em `http://localhost:5433`

O adminer estará rodando em `http://localhost:8082`

### Frontend

alterar a propriedade `ip` para o ip da máquina em `front\src\services\Api.ts`

```bash
cd front
npm install
npm run start
```

A aplicação mobile será iniciada no emulador ou dispositivo conectado.

## ⚙️ Configuração

### Variáveis de Ambiente

Crie um arquivo `.env` na pasta `back/`:

```env
# prod to production
PROFILE=dev

# security
TOKEN_ISSUER=LockBox
TOKEN_SECRET=secret
TOKEN_DURATION=15m

# database config to prod profile
DB_URL=jdbc:postgresql://docker_db_service/database
DB_USER=user
DB_PASSWORD=password
```

## 📄 Licença

Este projeto está sob a licença MIT - veja o arquivo [LICENSE](LICENSE) para detalhes.

## 👥 Autores

**Arthur Coreia** (arthurcorreia.dev@gmail.com)
- [GitHub](https://github.com/artucorreia/)
- [LinkedIn](https://www.linkedin.com/in/arthur-correia-2b07aa243/)