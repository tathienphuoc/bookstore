INSERT INTO `AUTHOR`(
    `full_name`,
    `date_of_brith`,
    `country`
)
VALUES(
    'Nguyễn Hữu Hưng',
    '1972-1-1',
    'Việt Nam'
);
INSERT INTO `AUTHOR`(
    `full_name`,
    `date_of_brith`,
    `country`
)
VALUES('Stephen Hawking', '1973-1-2', 'Anh');
INSERT INTO `AUTHOR`(
    `full_name`,
    `date_of_brith`,
    `country`
)
VALUES('Sigmund Freud', '1939-5-23', 'Áo');
INSERT INTO `AUTHOR`(
    `full_name`,
    `date_of_brith`,
    `country`
)
VALUES(
    'Nguyễn Nhật Ánh',
    '1985-7-3',
    'Việt Nam'
);
INSERT INTO `AUTHOR`(
    `full_name`,
    `date_of_brith`,
    `country`
)
VALUES(
    'Haruki Murakami',
    '1970-9-3',
    'Nhật'
);
INSERT INTO `AUTHOR`(
    `full_name`,
    `date_of_brith`,
    `country`
)
VALUES('Robin Sharma', '1976-4-30', 'Ấn Độ');
INSERT INTO `AUTHOR`(
    `full_name`,
    `date_of_brith`,
    `country`
)
VALUES('Benjamin Graham', '1969-5-19', 'Mỹ');
INSERT INTO `AUTHOR`(
    `full_name`,
    `date_of_brith`,
    `country`
)
VALUES(
    'George Charles Selden',
    '1930-4-1',
    'Mỹ'
);
INSERT INTO `AUTHOR`(
    `full_name`,
    `date_of_brith`,
    `country`
)
VALUES('Fujiko F Fujio', '1950-7-3', 'Nhật');
INSERT INTO `AUTHOR`(
    `full_name`,
    `date_of_brith`,
    `country`
)
VALUES(
    'Nguyễn Thanh Tùng',
    '1980-4-22',
    'Việt Nam'
);
INSERT INTO `AUTHOR`(
    `full_name`,
    `date_of_brith`,
    `country`
)
VALUES(
    'Lương Trọng Minh',
    '1989-6-16',
    'Việt Nam'
);
INSERT INTO `CATEGORY`(`name`)
VALUES('Khác');
INSERT INTO `CATEGORY`(`name`)
VALUES('Khoa học kỹ thuật');
INSERT INTO `CATEGORY`(`name`)
VALUES('Tiểu thuyết');
INSERT INTO `CATEGORY`(`name`)
VALUES('Kinh tế');
INSERT INTO `CATEGORY`(`name`)
VALUES('Thiếu nhi');
INSERT INTO `CATEGORY`(`name`)
VALUES('Ngoại ngữ');
INSERT INTO `CATEGORY`(`name`)
VALUES('Giải trí');
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Lập trình với Scratch 3.0',
    2,
    200000,
    2019,
    'Giới thiệu về ngôn ngữ lập trình kéo thả phổ biến nhất thế giới cho học sinh phổ thông, Scratch 3.0 (phiên bản mới, phát hành năm 2019). Cuốn sách kế thừa hoàn toàn những quan điểm về nội dung, cách trình bày của cuốn sách “Lập trình với Scratch” xuất bản năm 2016 tại NXBGD (viết cho phiên bản Scratch 2.0), đồng thời bổ sung những thông tin mới về giao diện, cách lập trình cũng như các khối lệnh của phiên bản Scratch 3.0. Giúp người học nhanh chóng làm chủ hoàn toàn cách sử dụng Scratch 3.0 thông qua từng bước hướng dẫn thiết kế và lập trình ra 05 chương trình mẫu theo cấp độ từ dễ đến khó. Trên cơ sở đó người học có thể tự tạo ra các ứng dụng trò chơi, ứng dụng hỗ trợ học tập nghiên cứu hoặc đơn giản như làm tấm thiệp hay bộ phim hoạt hình, tùy theo trình độ cũng như  ý tưởng của riêng mình.',
    1
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Lược sử thời gian',
    2,
    78000,
    2020,
    'Cùng với Vũ trụ trong vỏ hạt dẻ, Lược sử thời gian được xem là cuốn sách nổi tiếng và phổ biến nhất về vũ trụ học của Stephen Hawking, liên tục được nằm trong danh mục sách bán chạy nhất của các tạp chí nổi tiếng thế giới. Lược sử thời gian là cuốn sử thi về sự ra đời, sự hình thành và phát triển của vũ trụ. Tác giả đưa vào tác phẩm của mình toàn bộ tiến bộ tiến trình khám phá của trí tuệ loài người trên nhiều lĩnh vực: Triết học, Vật lý, Thiên văn học… Nhân dịp kỷ niệm lần in thứ 10 xin trân trong giới thiệu cùng bạn đọc.',
    2
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Vũ trụ trong vỏ hạt dẻ',
    2,
    80000,
    2017,
    'Lòng khát khao khám phá luôn là động lực cho trí sáng tạo của con người trong mọi lĩnh vực không chỉ trong khoa học. Điều đó được kiểm chứng trong quyển \"Vũ trụ trong vỏ hạt dẻ.\"',
    2
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Về giấc mơ và giải mã giấc mơ',
    2,
    120000,
    2019,
    'Cuốn sách này nhất định là đã mở rộng nhận thức của loài người về bản thân thêm cả toàn bộ lãnh vực cái vô thức và mở ra một hoàng lộ để hiểu được sự chuyển đổi từ cái suy nghĩ vô thức về ý thức, hội nhập về sự hiểu biết và nắm bắt được tác động trị liệu của việc hội nhập này. Nó vĩ đại hơn một công trình mang tầm thế kỷ.',
    3
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Mắt biếc',
    3,
    88000,
    2019,
    'Mắt biếc là một tác phẩm được nhiều người bình chọn là hay nhất của nhà văn Nguyễn Nhật Ánh. Tác phẩm này cũng đã được dịch giả Kato Sakae dịch sang tiếng Nhật để giới thiệu với độc giả Nhật Bản.
\“Tôi gửi tình yêu cho mùa hè, nhưng mùa hè không giữ nổi. Mùa hè chỉ biết ra hoa, phượng đỏ sân trường và tiếng ve nỉ non trong lá. Mùa hè ngây ngô, giống như tôi vậy. Nó chẳng làm được những điều tôi ký thác. Nó để Hà Lan đốt tôi, đốt rụi. Trái tim tôi cháy thành tro, rơi vãi trên đường về.\”
… Bởi sự trong sáng của một tình cảm, bởi cái kết thúc buồn, rất buồn khi xuyên suốt câu chuyện vẫn là những điều vui, buồn lẫn lộn…',
    4
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Rừng Na-uy',
    3,
    95000,
    2019,
    'Xuất bản lần đầu ở Nhật Bản năm 1987, Truyện Tiểu Thuyết Rừng Na Uy thực sự là một hiện tượng kỳ lạ với 4 triệu bản sách được bán ra, và theo thống kê hiện tại, cứ 7 người Nhật thì có 1 người đã đọc Rừng Na Uy. Tại Trung Quốc, Rừng Na Uy đã trở thành một hiện tượng văn hoá với hơn 1 triệu bản sách được tiêu thụ và được đánh giá là 1 trong 10 cuốn sách có ảnh hưởng lớn nhất ở đại lục trong thế kỷ 20.',
    5
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Biên niên ký chim vặn dây cót',
    3,
    160000,
    2020,
    'Toru Okada, một luật sư vừa bỏ việc, đang có một cuộc sống bình thường, giản dị bên cạnh người vợ Kumiko thì đột nhiên con mèo của anh biến mất. Ngay sau đó, vợ anh bỏ đi, để lại một lời nhắn rằng anh đừng cố đi tìm cô. Toru cố gắng đi tìm vợ và con mèo, nhưng việc tìm kiếm đó liên tục bị gián đoạn bởi sự xuất hiện của những nhân vật kỳ lạ trong cuộc sống của anh: Một cô gái điếm tâm thần gọi đến để quấy rối tình dục qua điện thoại, hai chị em thầy đồng, một cô bé 16 tuổi bị ám ảnh bởi cái chết của bạn trai gọi anh là “Chim vặn dây cót”, một cựu chiến binh kể lại cho anh câu chuyện về nỗi kinh hoàng của binh lính Nhật trong những năm đầu Chiến tranh Thế giới thứ hai tại Cao nguyên Mông Cổ và trên đất Trung Hoa. Tất cả những sự kiện kỳ quặc đó lại tiếp tục đẩy anh tới những sự kiện còn kỳ quặc hơn nữa.',
    5
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Nhà lãnh đạo không chức danh',
    4,
    66000,
    2017,
    'Suốt hơn 15 năm, Robin Sharma đã thầm lặng chia sẻ với những công ty trong danh sách Fortune 500 và nhiều người siêu giàu khác một công thức thành công đã giúp ông trở thành một trong những nhà cố vấn lãnh đạo được theo đuổi nhiều nhất thế giới. Đây là lần đầu tiên Sharma công bố công thức độc quyền này với bạn, để bạn có thể đạt được những gì tốt nhất, đồng thời giúp tổ chức của bạn có thể có những bước đột phá đến một cấp độ thành công mới trong thời đại thiên biến vạn hóa như hiện nay.',
    6
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Nhà đầu tư thông minh',
    4,
    140000,
    2018,
    'Là nhà tư vấn đầu tư vĩ đại nhất của thế kỷ 20, Benjamin Graham đã giảng dạy và truyền cảm hứng cho nhiều người trên khắp thế giới. Triết lý \“đầu tư theo giá trị\“ của Graham, bảo vệ nhà đầu tư khỏi những sai lầm lớn và dạy anh ta phát triển các chiến lược dài hạn, đã khiến Nhà đầu tư thông minh trở thành cẩm nang của thị trường chứng khoán kể từ lần xuất bản đầu tiên vào năm 1949.',
    7
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Tâm lý thị trường chứng khoán',
    4,
    75000,
    2017,
    'Được xuất bản năm 1912, đã trải qua hơn 100 năm nhưng Psychology of Stock Market với phiên bản tiếng Việt mang tên Tâm lý thị trường chứng khoán vẫn giữ nguyên được giá trị của nó cho đến hôm nay.',
    8
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Đô-ra-ê-mon tập 1',
    5,
    20000,
    2019,
    'Chuyện nổi tiếng về chú mèo máy thông minh Doraemon và các bạn.',
    9
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Đô-ra-ê-mon tập 2',
    5,
    20000,
    2019,
    'Chuyện nổi tiếng về chú mèo máy thông minh Doraemon và các bạn.',
    9
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Đô-ra-ê-mon tập 3',
    5,
    20000,
    2019,
    'Chuyện nổi tiếng về chú mèo máy thông minh Doraemon và các bạn.',
    9
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Hackers Ielts: Reading',
    6,
    190000,
    2019,
    'Bộ sách luyện thi IELTS đầu tiên có kèm giải thích đáp án chi tiết và hướng dẫn cách tự nâng band điểm.',
    10
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Hackers Ielts: Writing',
    6,
    165000,
    2019,
    'Bộ sách luyện thi IELTS đầu tiên có kèm giải thích đáp án chi tiết và hướng dẫn cách tự nâng band điểm.',
    10
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Hackers Ielts: Speaking',
    6,
    150000,
    2019,
    'Bộ sách luyện thi IELTS đầu tiên có kèm giải thích đáp án chi tiết và hướng dẫn cách tự nâng band điểm.',
    10
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Hackers Ielts: Listening',
    6,
    135000,
    2019,
    'Bộ sách luyện thi IELTS đầu tiên có kèm giải thích đáp án chi tiết và hướng dẫn cách tự nâng band điểm.',
    10
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Cờ vua - Tập 1: Những bài học đầu tiên',
    7,
    60000,
    2018,
    'Cờ vua là môn thể thao rèn luyện khả năng tư duy cao cho mỗi người chơi, đồng thời nó là một trò chơi giải trí mang tính sáng tạo. Cờ vua không chỉ có lịch sử lâu dài và được nhiều người yêu thích mà qua mỗi giai đoạn lại có bước đổi mới, phát triển phong phú hơn. Chúng ta có thể chơi cờ vua ở mọi lúc, mọi nơi, trong nhiều trường hợp và hoàn cảnh khác nhau.',
    11
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Cờ vua - Tập 2: Những bài học đầu tiên',
    7,
    73000,
    2018,
    'Cờ vua là môn thể thao rèn luyện khả năng tư duy cao cho mỗi người chơi, đồng thời nó là một trò chơi giải trí mang tính sáng tạo. Cờ vua không chỉ có lịch sử lâu dài và được nhiều người yêu thích mà qua mỗi giai đoạn lại có bước đổi mới, phát triển phong phú hơn. Chúng ta có thể chơi cờ vua ở mọi lúc, mọi nơi, trong nhiều trường hợp và hoàn cảnh khác nhau.',
    11
);
INSERT INTO `BOOK`(
    `title`,
    `category_id`,
    `price`,
    `publish_year`,
    `description`,
    `author_id`
)
VALUES(
    'Cờ vua - Tập 3: Những bài học đầu tiên',
    7,
    80000,
    2018,
    'Cờ vua là môn thể thao rèn luyện khả năng tư duy cao cho mỗi người chơi, đồng thời nó là một trò chơi giải trí mang tính sáng tạo. Cờ vua không chỉ có lịch sử lâu dài và được nhiều người yêu thích mà qua mỗi giai đoạn lại có bước đổi mới, phát triển phong phú hơn. Chúng ta có thể chơi cờ vua ở mọi lúc, mọi nơi, trong nhiều trường hợp và hoàn cảnh khác nhau.',
    11
);