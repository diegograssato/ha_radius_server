# users(mac/user) — gerado automaticamente pelo addon FreeRADIUS
# Não edite manualmente — use as opções do addon.

{{ range .users }}
{{ if eq .entity_type "mac" }}
{{ .username }} Auth-Type := Accept
	Fall-Through = no

{{ else if .password }}
{{ .username }} Cleartext-Password := "{{ .password }}"
	Fall-Through = no

{{ end }}
{{ end }}
