# mods-available/eap — gerado automaticamente pelo addon FreeRADIUS
eap {
    default_eap_type = {{ .eap.default_eap_type }}
    timer_expire = {{ .eap.timer_expire }}
    ignore_unknown_eap_types = {{ if .eap.ignore_unknown_eap_types }}yes{{ else }}no{{ end }}
    cisco_accounting_username_bug = no
    max_sessions = ${max_requests}

    md5 {
    }

    leap {
    }

    gtc {
        auth_type = PAP
    }

    tls-config tls-common {
        private_key_password = {{ .eap.tls.private_key_password }}
        private_key_file = {{ .eap.tls.private_key_file }}
        certificate_file = {{ .eap.tls.certificate_file }}
        ca_file = {{ .eap.tls.ca_file }}
        # dh_file removed: unnecessary in FreeRADIUS 3.0.27+
        random_file = /dev/urandom
        fragment_size = 1024
        include_length = yes
        auto_chain = yes
        check_crl = no
        cipher_list = "{{ .eap.tls.cipher_list }}"
        ecdh_curve = "prime256v1"

        cache {
            enable = no
            lifetime = 24
            store {
                Tunnel-Private-Group-Id
            }
        }

        verify {
        }

        ocsp {
            enable = no
            override_cert_url = yes
            url = "http://127.0.0.1/ocsp/"
        }
    }

    tls {
        tls = tls-common
    }

    ttls {
        tls = tls-common
        default_eap_type = {{ .eap.ttls.default_eap_type }}
        copy_request_to_tunnel = {{ .eap.ttls.copy_request_to_tunnel }}
        use_tunneled_reply = {{ .eap.ttls.use_tunneled_reply }}
        virtual_server = "{{ .eap.ttls.virtual_server }}"
    }

    peap {
        tls = tls-common
        default_eap_type = {{ .eap.peap.default_eap_type }}
        copy_request_to_tunnel = {{ .eap.peap.copy_request_to_tunnel }}
        use_tunneled_reply = {{ .eap.peap.use_tunneled_reply }}
        virtual_server = "{{ .eap.peap.virtual_server }}"
    }

    mschapv2 {
    }
}
