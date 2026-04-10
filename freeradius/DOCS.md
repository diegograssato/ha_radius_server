# Addon FreeRADIUS — Documentação

## Configuração

### Opções Básicas

| Opção | Padrão | Descrição |
|-------|--------|-----------|
| `auth_port` | `1812` | Porta UDP de autenticação |
| `acct_port` | `1813` | Porta UDP de contabilidade |
| `log_level` | `1` | Nível de log (0–5) |
| `log_auth` | `false` | Loga tentativas de autenticação |
| `log_auth_badpass` | `false` | Loga senhas incorretas |
| `log_auth_goodpass` | `false` | Loga autenticações bem-sucedidas |

### Clientes RADIUS (NAS)

Configure os dispositivos que enviarão requisições ao servidor RADIUS (ex: Access Points, switches):

```yaml
clients:
  - name: ap_sala
    ipaddr: "192.168.1.10"
    secret: "meu_segredo_forte"
  - name: switch_core
    ipaddr: "192.168.1.1"
    secret: "outro_segredo"
```

> **Segurança**: Use segredos longos e aleatórios (mínimo 16 caracteres).

### Usuários Locais

```yaml
users:
  - username: joao
    password: "senha_segura_123"
    group: "funcionarios"
  - username: maria
    password: "outra_senha_456"
```

### Configuração EAP

#### EAP/PEAP + MS-CHAPv2 (recomendado para Wi-Fi empresarial)

```yaml
eap:
  default_eap_type: peap
  peap:
    default_eap_type: mschapv2
    use_tunneled_reply: yes
```

#### EAP-TLS com certificados customizados

```yaml
eap:
  default_eap_type: tls
  tls:
    private_key_file: "/ssl/servidor.key"
    certificate_file: "/ssl/servidor.pem"
    ca_file: "/ssl/ca.pem"
    private_key_password: "senha_da_chave"
    dh_file: "/etc/freeradius/certs/dh"
    cipher_list: "DEFAULT"
```

> Se os arquivos de certificado **não forem especificados**, o addon gera automaticamente
> um certificado auto-assinado na primeira inicialização.

### Integração SQL (opcional)

#### SQLite (banco local — sem instalação adicional)

```yaml
sql:
  enabled: true
  driver: "rlm_sql_sqlite"
  dialect: "sqlite"
  radius_db: "/data/radius.db"
```

#### MySQL/MariaDB

```yaml
sql:
  enabled: true
  driver: "rlm_sql_mysql"
  dialect: "mysql"
  server: "192.168.1.100"
  port: 3306
  login: "radius"
  password: "senha_sql"
  radius_db: "radius"
```

#### PostgreSQL

```yaml
sql:
  enabled: true
  driver: "rlm_sql_postgresql"
  dialect: "postgresql"
  server: "192.168.1.100"
  port: 5432
  login: "radius"
  password: "senha_sql"
  radius_db: "radius"
```

### Redes Permitidas

```yaml
allow_hosts:
  - 10.0.0.0/8
  - 172.16.0.0/12
  - 192.168.0.0/16
```

## Estrutura de Arquivos Gerada

O addon gera automaticamente os seguintes arquivos de configuração:

| Arquivo | Descrição |
|---------|-----------|
| `/etc/freeradius/radiusd.conf` | Configuração principal |
| `/etc/freeradius/clients.conf` | Clientes RADIUS (NAS) |
| `/etc/freeradius/users` | Usuários locais |
| `/etc/freeradius/mods-available/eap` | Módulo EAP |
| `/etc/freeradius/mods-available/sql` | Módulo SQL (se habilitado) |
| `/etc/freeradius/sites-enabled/default` | Virtual server padrão |
| `/etc/freeradius/sites-enabled/inner-tunnel` | Tunnel server (EAP/PEAP/TTLS) |

## Testando a Autenticação

Com o addon rodando, utilize `radtest` de outra máquina na rede:

```bash
radtest usuario senha IP_DO_HA 0 segredo_do_cliente
```

Exemplo:
```bash
radtest joao senha_segura_123 192.168.1.50 0 meu_segredo_forte
```

Resposta esperada para sucesso:
```
Received Access-Accept Id 1 from 192.168.1.50:1812 to ...
```

## Integração com Access Points

### Exemplo — Unifi

1. No Unifi Controller, crie um perfil RADIUS apontando para o IP do Home Assistant na porta 1812
2. Configure o segredo compartilhado igual ao configurado em `clients`
3. Crie um SSID usando WPA2/WPA3 Enterprise com o perfil RADIUS criado

### Exemplo — MikroTik

```
/radius add address=IP_HA secret=segredo service=wireless port=1812
/interface wireless security-profiles set default authentication-types=wpa2-eap radius-mac-authentication=yes
```

## Troubleshooting

### O servidor não inicia

Verifique os logs do addon. Erros comuns:
- **Configuração inválida**: o script de init valida a configuração antes de subir o serviço
- **Porta em uso**: mude `auth_port` ou `acct_port` para portas livres

### Autenticação rejeitada

Ative o modo debug aumentando o `log_level` para `5` e verifique os logs.

### Certificado TLS inválido

Se usar certificados customizados, verifique se os caminhos estão corretos e os arquivos acessíveis. O addon suporta certificados do diretório `/ssl` do Home Assistant.
