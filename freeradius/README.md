# Home Assistant Addon: FreeRADIUS

Servidor RADIUS para autenticação de rede usando [FreeRADIUS](https://freeradius.org/).

## Sobre

Este addon permite executar um servidor FreeRADIUS no Home Assistant, possibilitando:

- Autenticação de usuários Wi-Fi via WPA2/WPA3-Enterprise (EAP/PEAP, EAP/TTLS, EAP-TLS)
- Gerenciamento de clientes RADIUS (NAS) via configuração do addon
- Cadastro de usuários locais ou integração com banco de dados SQL
- Log completo de autenticações e contabilidade

## Funcionalidades

- **EAP/PEAP + MS-CHAPv2** — método mais usado em redes corporativas
- **EAP/TTLS** — autenticação tunelada altamente compatível
- **EAP-TLS** — autenticação por certificado cliente (máxima segurança)
- **SQL Backend** — suporte a SQLite (local), MySQL e PostgreSQL
- **Multi-arquitetura** — amd64, aarch64 e armv7

## Configuração Rápida

1. Adicione o repositório ao Home Assistant
2. Instale o addon **FreeRADIUS**
3. Configure pelo menos um cliente RADIUS com IP e segredo compartilhado
4. Adicione os usuários desejados
5. Inicie o addon

## Documentação

Consulte [DOCS.md](DOCS.md) para configuração detalhada.