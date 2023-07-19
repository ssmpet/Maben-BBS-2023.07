
SELECT l.bid, l.uid, l.title, l.modTime, l.viewCount, l.replyCount, r.uname 
	FROM board AS l JOIN users AS r 
	ON l.uid=r.uid 
	WHERE l.isDeleted=0 AND title LIKE "%%"
	ORDER BY modTime DESC, bid DESC LIMIT 10 OFFSET 0;