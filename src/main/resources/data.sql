# ALTER TABLE member ADD UNIQUE (email);
# ALTER TABLE department ADD UNIQUE (link);

INSERT IGNORE INTO member (email, name, reg_date, mod_date, han_role) VALUES ('admin@hanspace.com', 'superAdmin', NOW(), NOW(), 0);

# INSERT IGNORE INTO department (reg_date, mod_date, site_name, user_accept, max_reserve_count, extra_info, link, logo, extra_info, dept_image)
# VALUES (NOW(), NOW(), '#FFFFFF', true, 10, '학부, 교수이름', 'HANSPACE', '로고', 'extraInfo', 'dept1');
