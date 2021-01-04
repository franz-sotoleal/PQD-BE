-- Init test user (password is "password")

-- DO NOT CHANGE OR DELETE THESE VALUES - INTEGRATION TESTS USE THESE (IF YOU CHANGE, THEN MAKE SURE THAT INT. TEST CAN INITIALIZE THEM ELSEWHERE)

INSERT INTO public.user(id, first_name, last_name, username, email, password) VALUES (1, 'john', 'doe', 'user', 'john.doe@mail.com', '$2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6');
INSERT INTO public.sq_info(id, base_url, component_name, token) VALUES (1, 'http://localhost:9000', 'ESI-builtit', '9257cc3a6b0610da1357f73e03524b090658553d');
INSERT INTO public.product(id, name, token, sq_info_id) VALUES (1, 'Demo Product', '8257cc3a6b0610da1357f73e03524b090658553a', 1);
INSERT INTO public.user_product_claims(user_id, product_id, claim_level) VALUES (1, 1, 'admin');
INSERT INTO public.release_info_sq(id, sec_rating, rel_rating, maint_rating, sec_vulns, rel_bugs, maint_debt, maint_smells) VALUES (1, 1.0, 3.0, 2.0, 0, 5, 326, 65);
INSERT INTO public.release_info(id, product_id, quality_level, release_info_sq_id) VALUES (1, 1, 0.75, 1);

