CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX IF NOT EXISTS idx_users_user_name_trgm ON users USING gin (user_name gin_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_users_full_name_trgm ON users USING gin (full_name gin_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_users_phone_trgm ON users USING gin (phone gin_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_users_email_id_trgm ON users USING gin (email_id gin_trgm_ops);
