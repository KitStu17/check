CREATE TABLE IF NOT EXISTS file_extension (
    seqno BIGSERIAL PRIMARY KEY,
    extension_name VARCHAR(200) NOT NULL,
    is_fixed BOOLEAN NOT NULL DEFAULT FALSE,
    is_blocked BOOLEAN NOT NULL DEFAULT FALSE,
    update_dt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX idx_extension_name ON file_extension(extension_name);

INSERT INTO file_extension (extension_name, is_fixed, is_blocked) VALUES
('bat', TRUE, FALSE),
('cmd', TRUE, FALSE),
('com', TRUE, FALSE),
('cpl', TRUE, FALSE),
('exe', TRUE, FALSE),
('scr', TRUE, FALSE),
('js', TRUE, FALSE);

COMMENT ON TABLE public.file_extension IS '파일 확장자 관리 테이블';
COMMENT ON COLUMN public.file_extension.seqno is '일련번호';
COMMENT ON COLUMN public.file_extension.extension_name is '확장자명';
COMMENT ON COLUMN public.file_extension.is_fixed is '고정 확장자 여부 확인';
COMMENT ON COLUMN public.file_extension.is_blocked is '차단 여부 확인';
COMMENT ON COLUMN public.file_extension.update_dt is '마지막 상태 변경 일자';