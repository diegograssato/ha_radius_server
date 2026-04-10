prefix = /usr
exec_prefix = /usr
sysconfdir = /etc/freeradius
localstatedir = /var
sbindir = ${exec_prefix}/sbin
logdir = /var/log/freeradius
raddbdir = ${sysconfdir}
radacctdir = ${logdir}/radacct

name = freeradius

confdir = ${raddbdir}
modconfdir = ${confdir}/mods-config
certdir = ${confdir}/certs
cadir   = ${confdir}/certs
run_dir = ${localstatedir}/run/freeradius
db_dir = ${localstatedir}/lib/freeradius
libdir = /usr/lib/freeradius
pidfile = ${run_dir}/${name}.pid

correct_escapes = true

max_request_time = 30
cleanup_delay = 5
max_requests = 16384
hostname_lookups = no

log {
    destination = stdout
    colourise = yes
    file = ${logdir}/radius.log
    syslog_facility = daemon
    stripped_names = no
    auth = {{ .log_auth }}
    auth_badpass = {{ .log_auth_badpass }}
    auth_goodpass = {{ .log_auth_goodpass }}
    msg_denied = "You are denied access to the requested service: %%{User-Name}"
}

checkrad = ${sbindir}/checkrad

security {
    user = radius
    group = radius
    allow_core_dumps = no
    max_attributes = 200
    reject_delay = 1
    status_server = yes
    allow_vulnerable_openssl = no
}

proxy_requests = no

$INCLUDE clients.conf

thread pool {
    start_servers = 5
    max_servers = 32
    min_spare_servers = 3
    max_spare_servers = 10
    max_requests_per_server = 0
    auto_limit_acct = no
}

modules {
    $INCLUDE mods-enabled/
}

instantiate {
}

policy {
    $INCLUDE policy.d/
}

$INCLUDE sites-enabled/
