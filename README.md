<img src="https://raw.githubusercontent.com/diegograssato/ha_radius_access/refs/heads/main/icon.png" align="left" width="100" height="100"  alt="Radius logo">

# Home Assistant Addon: FreeRADIUS

Servidor RADIUS para autenticação de rede usando [FreeRADIUS](https://freeradius.org/).


**Este componente configurará as seguintes plataformas.**

Platform | Description
-- | --
`ha_radius_server` | Home Assistant Addon: FreeRADIUS.
[ha_radius_access] | Painel administrativo para Home Assistant.



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
- **Usuários e MAC** — suporte para autenticação por usuário (senha) ou MAC (Auth-Type Accept)
- **SQL Backend** — suporte a arquivo, SQLite (local), MySQL e PostgreSQL com sincronização automática
- **Sincronização Automática** — add/update/delete de usuários e clientes NAS propagam automaticamente para o banco
- **Multi-arquitetura** — amd64, aarch64 e armv7

## Configuração Rápida

1. Adicione o repositório ao Home Assistant
2. Instale o addon **FreeRADIUS**
3. Configure pelo menos um cliente RADIUS com IP e segredo compartilhado
4. Adicione os usuários desejados
5. Inicie o addon

## Documentação

Consulte [DOCS.md](DOCS.md) para configuração detalhada.


[ha_radius_access]: https://github.com/diegograssato/ha_radius_access