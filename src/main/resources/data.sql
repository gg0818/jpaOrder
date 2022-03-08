select * from store;
insert into store(store_name, status) values ("굴림당","OPEN");
insert into store(store_name, status) values ("다농이네","OPEN");
insert into store(store_name, status) values ("밥푸드","OPEN");
insert into store(store_name, status) values ("네모네모","OPEN");
insert into store(store_name, status) values ("한와담블랙","OPEN");
insert into store(store_name, status) values ("을지로보석","OPEN");
insert into store(store_name, status) values ("해담은","OPEN");

select * from menu;

insert into menu(info, name, menu_img, price, store_id)
values("NO밀가루로 부담 없이 즐기는 수제만두", "굴림당 수제 굴림만두","/images/food_img1.jpg","11800",1);

insert into menu(info, name, menu_img, price, store_id)
values("앙증 맞은 크기부터 한 입 가득 특품까지", "산청 설향 딸기","/images/food_img2.jpg","15800",2);

insert into menu(info, name, menu_img, price, store_id)
values("준비는 간단하게 식탁은 풍성하게", "밥도둑 반찬 4종","/images/food_img3.jpg","3500",3);

insert into menu(info, name, menu_img, price, store_id)
values("퐁실한 카스텔라 속 부드러운 수제 크림", "수플레 카스텔라 선물세트","/images/food_img4.jpg","6600",4);

insert into menu(info, name, menu_img, price, store_id)
values("한와담블랙부터 도연하다까지", "한와담블랙 한우곰탕","/images/food_img5.jpg","10900",5);

insert into menu(info, name, menu_img, price, store_id)
values("특별 레시피로 만들어 깊은 풍미가 가득해요", "을지로보석 비빔마라우동","/images/food_img6.jpg","6900",6);

insert into menu(info, name, menu_img, price, store_id)
values("오직 맛있는 음식을 위해 연구합니다.", "해담은 전복 한상","/images/food_img7.jpg","6900",7);

insert into menu(info, name, menu_img, price, store_id)
values ("얇은 껍질 속 가득 찬 새콤달콤 과즙", "제주 천혜향", "/images/food_img8.jpg", "17900", 2);




