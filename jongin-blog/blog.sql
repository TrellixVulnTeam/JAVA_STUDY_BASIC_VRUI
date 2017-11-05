-- MEMBERS ���̺�
CREATE TABLE MEMBERS (
    member_no number(30),
    id varchar2(30) not null unique,
    pw varchar2(30) not null,
    PRIMARY KEY (member_no)
);

--member_no ������
create sequence member_no_sq;

-- LOGOS ���̺� ����
create table LOGOS(
    logo_no number(30),
    blog_no number(30) not null,
    file_path varchar2(1000) not null,
    ori_name varchar2(200) not null,
    system_name varchar2(200) not null,
    file_size number(30) not null,
    PRIMARY KEY (logo_no)
);

--logo_no ������
create sequence logo_no_sq;

-- BLOGS ���̺� ����
create table BLOGS(
    blog_no number(30),
    member_no number(30) not null,
    title varchar2(30) not null,
    tag varchar2(30) default '�±׸� �Է��ϼ���.',
    logo_no number(30) not null,
    show_num number(3) default 1,
    visit number(30) default 1,
    FOREIGN KEY(member_no) REFERENCES MEMBER(member_no),
    FOREIGN KEY(logo_no) REFERENCES LOGO(logo_no),
    PRIMARY KEY (blog_no)
);

--blog_no ������
create sequence blog_no_sq;

-- CATEGORYS ���̺� ����
create table CATEGORYS(
    category_no number(30),
    blog_no number(30) not null,
    name varchar2(30) not null,
    show_type varchar2(30) default 'TITLE',
    show_num number(3) default 1,
    description varchar2(100) not null,
    FOREIGN KEY(blog_no) REFERENCES BLOG(blog_no),
    PRIMARY KEY (category_no)
);

-- category_no ������
create sequence category_no;

-- POSTS ���̺� ����
create table POSTS(
    post_no number(30),
    blog_no number(30) not null,
    member_no number(30) not null,
    category_no number(30) not null,
    title varchar2(200) not null,
    writer varchar2(30) not null,
    content varchar2(4000) not null,
    reg_date date default sysdate,
    update_date date default sysdate,
    visit number(30) default 1,
    FOREIGN KEY(blog_no) REFERENCES BLOG(blog_no),
    FOREIGN KEY(member_no) REFERENCES MEMBER(member_no),
    FOREIGN KEY(category_no) REFERENCES CATEGORY(category_no),
    PRIMARY KEY (post_no)
);

-- post_no ������
create sequence post_no_sq;

-- COMMENTS ���̺� ����
create table COMMENTS(
    comment_no number(30),
    post_no number(30) not null,
    writer varchar2(30) not null,
    content varchar2(4000) not null,
    reg_date date default sysdate,
    update_date date default sysdate,
    FOREIGN KEY(post_no) REFERENCES POST(post_no),
    PRIMARY KEY (comment_no)
);

-- comment_no ������
create sequence comment_no_sq;

--FILES ���̺� ����
create table FILES(
    file_no number(30),
    post_no number(30) not null,
    file_path varchar2(1000) not null,
    ori_name varchar2(200) not null,
    system_name varchar2(200) not null,
    file_size number(30) not null,
    FOREIGN KEY(post_no) REFERENCES POST(post_no),
    PRIMARY KEY (file_no)
);

-- file_no ������
create sequence file_no_sq;