ARG BUILD_FROM
FROM $BUILD_FROM

# Environment
ENV LANG=C.UTF-8

# Install FreeRADIUS and dependencies
# mschap is bundled in the base freeradius package on Alpine 3.21
RUN apk add --no-cache \
    freeradius \
    freeradius-eap \
    freeradius-sql \
    freeradius-mysql \
    freeradius-sqlite \
    freeradius-utils \
    sqlite \
    openssl \
    jq \
    bash

# Adiciona dicionário Mikrotik ao dictionary principal do FreeRADIUS
RUN echo '$INCLUDE /usr/share/freeradius/dictionary.mikrotik' >> /etc/raddb/dictionary

# Prepare runtime directories matching Alpine radiusd.conf defaults:
# run_dir=/run/radiusd, logdir=/var/log/radius, db_dir=/var/lib/radiusd, cachedir=/var/cache/radiusd
RUN mkdir -p /run/radiusd /var/log/radius /var/lib/radiusd /var/cache/radiusd \
    && chown radius:radius /run/radiusd /var/log/radius /var/lib/radiusd /var/cache/radiusd

# Generate default DH params into the Alpine config certs dir
RUN openssl dhparam -out /etc/raddb/certs/dh 2048 2>/dev/null \
    && chown radius:radius /etc/raddb/certs/dh \
    && chmod 600 /etc/raddb/certs/dh

# Copy rootfs
COPY rootfs /

# Set execute permissions on s6-overlay scripts
RUN chmod a+x \
    /etc/s6-overlay/s6-rc.d/init-freeradius/run \
    /etc/s6-overlay/s6-rc.d/init-freeradius/up \
    /etc/s6-overlay/s6-rc.d/init-freeradius-sites/run \
    /etc/s6-overlay/s6-rc.d/init-freeradius-sites/up \
    /etc/s6-overlay/s6-rc.d/freeradius/run \
    /etc/s6-overlay/s6-rc.d/freeradius/finish

# Ensure config dirs are writable by root during init
RUN chown -R root:radius /etc/raddb \
    && chmod -R g+rX /etc/raddb \
    && chmod g+w /etc/raddb/mods-available /etc/raddb/sites-enabled /etc/raddb/mods-enabled \
    && chmod 750 /etc/raddb/certs \
    && chown root:radius /etc/raddb/certs

HEALTHCHECK --start-period=10s --interval=30s --timeout=10s \
    CMD radtest dummy dummy 127.0.0.1 0 testing123 2>&1 | grep -E "(Access-Accept|Access-Reject)" || exit 1
