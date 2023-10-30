ALTER TABLE member ADD UNIQUE (email);
ALTER TABLE department ADD UNIQUE (link);

INSERT IGNORE INTO member (email, name, reg_date, mod_date) VALUES ('user1@example.com', 'user1', NOW(), NOW());

INSERT IGNORE INTO department (reg_date, mod_date, color, site_name, user_accept, max_reserve_count, extra_info, link, logo, site_info_title, site_info_detail)
VALUES (NOW(), NOW(), '#FFFFFF', 'hanspace', true, 10, '학부, 교수이름', 'HANSPACE', '로고', '사이트 설명 타이틀', '사이트 설명 디테일');
