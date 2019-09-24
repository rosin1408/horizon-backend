FROM postgres:11.5-alpine

# Copy the database initialize script:
# Contents of /docker-entrypoint-initdb.d are run on startup
RUN mkdir -p /images/

COPY pg_hba.conf /usr/share/postgresql/11.5/
COPY postgresql.conf /usr/share/postgresql/11.5/

VOLUME ./data:/var/lib/postgresql/data

# Default values for passwords and database name. Can be overridden on docker run
ENV POSTGRES_USER horizon
ENV POSTGRES_DB horizon
ENV POSTGRES_PASSWORD=horizon