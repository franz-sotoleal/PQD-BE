-- Init test user (password is "password")

-- DO NOT CHANGE OR DELETE THESE VALUES - INTEGRATION TESTS USE THESE (IF YOU CHANGE, THEN MAKE SURE THAT INT. TEST CAN INITIALIZE THEM ELSEWHERE)

-- USER AND SONARQUBE
INSERT INTO public.user(first_name, last_name, username, email, password) VALUES ('john', 'doe', 'user', 'john.doe@mail.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6');
INSERT INTO public.user(first_name, last_name, username, email, password) VALUES ('jack', 'dope', 'admin', 'jack.dope@mail.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6');
INSERT INTO public.sq_info(base_url, component_name, token) VALUES ('http://localhost:9000', 'ESI-builtit', '9257cc3a6b0610da1357f73e03524b090658553d');
INSERT INTO public.sq_info(base_url, component_name, token) VALUES ('http://localhost:9000', 'ESI-builtit', '9257cc3a6b0610da1357f73e03524b090658553d');
INSERT INTO public.sq_info(base_url, component_name, token) VALUES ('http://localhost:9000', 'ESI-builtit', '9257cc3a6b0610da1357f73e03524b090658553d');
INSERT INTO public.product(name, token, sq_info_id) VALUES ('Demo Product', '8257cc3a6b0610da1357f73e03524b090658553a', 1);
INSERT INTO public.product(name, token, sq_info_id) VALUES ('Demo Product 2', '7257cc3a6b0610da1357f73e03524b090658553b', 51);
INSERT INTO public.product(name, token, sq_info_id) VALUES ('Demo Product 3', '6257cc3a6b0610da1357f73e03524b090658553c', 101);
INSERT INTO public.user_product_claim(user_id, product_id, claim_level) VALUES (1, 1, 'admin');
INSERT INTO public.user_product_claim(user_id, product_id, claim_level) VALUES (1, 51, 'admin');
INSERT INTO public.user_product_claim(user_id, product_id, claim_level) VALUES (51, 101, 'admin');
INSERT INTO public.release_info_sq(sec_rating, rel_rating, maint_rating, sec_vulns, rel_bugs, maint_debt, maint_smells) VALUES (1.0, 3.0, 2.0, 0, 5, 326, 65);
INSERT INTO public.release_info_sq(sec_rating, rel_rating, maint_rating, sec_vulns, rel_bugs, maint_debt, maint_smells) VALUES (2.0, 1.0, 2.0, 0, 5, 326, 65);
INSERT INTO public.release_info_sq(sec_rating, rel_rating, maint_rating, sec_vulns, rel_bugs, maint_debt, maint_smells) VALUES (3.0, 1.0, 2.0, 0, 5, 326, 65);
INSERT INTO public.release_info_sq(sec_rating, rel_rating, maint_rating, sec_vulns, rel_bugs, maint_debt, maint_smells) VALUES (1.0, 1.0, 2.0, 0, 5, 326, 65);
INSERT INTO public.release_info_sq(sec_rating, rel_rating, maint_rating, sec_vulns, rel_bugs, maint_debt, maint_smells) VALUES (1.0, 2.0, 1.0, 0, 5, 326, 65);
INSERT INTO public.release_info_sq(sec_rating, rel_rating, maint_rating, sec_vulns, rel_bugs, maint_debt, maint_smells) VALUES (1.0, 3.0, 1.0, 0, 5, 326, 65);
INSERT INTO public.release_info_sq(sec_rating, rel_rating, maint_rating, sec_vulns, rel_bugs, maint_debt, maint_smells) VALUES (1.0, 3.0, 2.0, 0, 5, 326, 65);
INSERT INTO public.release_info_sq(sec_rating, rel_rating, maint_rating, sec_vulns, rel_bugs, maint_debt, maint_smells) VALUES (1.0, 3.0, 3.0, 0, 5, 326, 65);
INSERT INTO public.release_info_sq(sec_rating, rel_rating, maint_rating, sec_vulns, rel_bugs, maint_debt, maint_smells) VALUES (1.0, 5.0, 2.0, 0, 5, 326, 65);
INSERT INTO public.release_info_sq(sec_rating, rel_rating, maint_rating, sec_vulns, rel_bugs, maint_debt, maint_smells) VALUES (1.0, 5.0, 2.0, 0, 5, 326, 65);
INSERT INTO public.release_info(product_id, quality_level, release_info_sq_id) VALUES (1, 0.75, 1);
INSERT INTO public.release_info(product_id, quality_level, release_info_sq_id) VALUES (1, 0.50, 51);
INSERT INTO public.release_info(product_id, quality_level, release_info_sq_id) VALUES (1, 0.35, 101);
INSERT INTO public.release_info(product_id, quality_level, release_info_sq_id) VALUES (1, 0.40, 151);
INSERT INTO public.release_info(product_id, quality_level, release_info_sq_id) VALUES (1, 0.8, 201);
INSERT INTO public.release_info(product_id, quality_level, release_info_sq_id) VALUES (51, 0.50, 251);
INSERT INTO public.release_info(product_id, quality_level, release_info_sq_id) VALUES (51, 0.75, 301);
INSERT INTO public.release_info(product_id, quality_level, release_info_sq_id) VALUES (51, 0.75, 351);
INSERT INTO public.release_info(product_id, quality_level, release_info_sq_id) VALUES (101, 0.9, 401);
INSERT INTO public.release_info(product_id, quality_level, release_info_sq_id) VALUES (101, 0.85, 451);

-- JIRA
INSERT INTO public.jira_info(base_url, board_id, user_email, token) VALUES ('https://kert944.atlassian.net', 1, 'prinkkert@gmail.com', 'dlNrqUp5na04fQyacxcx58EF');
UPDATE public.product SET jira_info_id = 1 WHERE id = 1;
INSERT INTO public.release_info_jira_sprint(sprint_id, board_id, name, start_time, end_time, goal, browser_url) VALUES (1, 1, 'PT Sprint 1', '2021-01-18T18:31:09.872', '2021-01-25T18:31:07.000', 'Add functionality A to the product', 'https://kert944.atlassian.net/issues/?jql=Sprint%20%3D%201');
INSERT INTO public.jira_issue(jira_sprint_id, issue_id, key, description, icon_url, name, browser_url) VALUES (1, 10001, 'PT-2', 'Funktsionaalsus või väljendatud funktsioon kui kasutaja eesmärk.', 'https://kert944.atlassian.net/secure/viewavatar?size=medium&avatarId=10315&avatarType=issuetype', 'Lugu', 'https://kert944.atlassian.net/browse/PT-2');
INSERT INTO public.jira_issue(jira_sprint_id, issue_id, key, description, icon_url, name, browser_url) VALUES (1, 10000, 'PT-1', 'Funktsionaalsus või väljendatud funktsioon kui kasutaja eesmärk.', 'https://kert944.atlassian.net/secure/viewavatar?size=medium&avatarId=10315&avatarType=issuetype', 'Lugu', 'https://kert944.atlassian.net/browse/PT-1');
UPDATE public.release_info SET release_info_jira_sprint = 1 WHERE product_id = 1;
