# clients.conf — gerado automaticamente pelo addon FreeRADIUS
# Não edite manualmente.

# Cliente local para healthcheck e testes internos
client localhost {
    ipaddr = 127.0.0.1
    secret = "testing123"
    shortname = localhost
    nas_type = other
    require_message_authenticator = true
}

{{ range .clients }}
client {{ .name }} {
    ipaddr = {{ .ipaddr }}
    secret = "{{ .secret }}"
    shortname = {{ .name }}
    nas_type = other
    require_message_authenticator = true
}
{{ end }}
