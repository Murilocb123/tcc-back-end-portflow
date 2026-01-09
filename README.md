# Portflow Backend - TCC

Este projeto é o backend do Portflow, desenvolvido como parte do Trabalho de Conclusão de Curso (TCC). Ele foi construído utilizando Java e Spring Boot, com foco em gestão de portfólios de investimentos.

## Funcionalidades Principais
- **Gestão de Portfólios**: Cadastro, atualização e consulta de portfólios de ativos financeiros.
- **Gestão de Ativos**: Controle de ativos, histórico de preços, eventos e transações.
- **Gestão de Corretoras**: Cadastro e consulta de corretoras.
- **Dashboard**: Visualização de indicadores e retornos dos portfólios.

## Recursos de Autenticação
O sistema implementa autenticação baseada em JWT (JSON Web Token), garantindo segurança no acesso às APIs. Usuários devem se autenticar para acessar recursos protegidos.

- **Endpoints de login e registro**
- **Proteção de rotas**: Apenas usuários autenticados podem acessar dados sensíveis.
- **Validação de token**: Tokens são validados em cada requisição protegida.

## Multitenancy
O projeto suporta multitenancy, permitindo que múltiplos usuários/empresas utilizem o sistema de forma isolada:

- **Isolamento de dados**: Cada usuário possui acesso apenas aos seus próprios dados.
- **Identificação de tenant**: As entidades principais possuem identificador de tenant para garantir o isolamento.
- **Estrutura flexível**: Permite expansão para múltiplos tenants sem necessidade de grandes alterações na arquitetura.

## Estrutura do Projeto
```
src/main/java/br/com/murilocb123/portflow/
├── auth/           # Autenticação e segurança
├── controller/     # Controllers REST
├── domain/         # Entidades de domínio
├── dto/            # Data Transfer Objects
├── infra/          # Infraestrutura
├── mapper/         # Mapeamento de entidades
├── repositories/   # Repositórios JPA
├── service/        # Regras de negócio
├── utils/          # Utilitários
```

## Configuração
- **Banco de dados**: Configurado via `application.yaml`.
- **Migrações**: Scripts em `src/main/resources/db/migration/`.
- **Testes**: Localizados em `src/test/java/br/com/murilocb123/portflow/`.

## Como executar
1. Instale o Java 17+
2. Execute `./mvnw spring-boot:run` ou utilize sua IDE favorita.

## Contato
Desenvolvido por Murilo C. B. como parte do TCC.

