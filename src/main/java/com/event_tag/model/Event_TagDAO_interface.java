package com.event_tag.model;
import com.event_tag.model.*;
import java.util.*;

public interface Event_TagDAO_interface {
	public void insert(Event_TagVO event_tagVO);
	public void update(Event_TagVO event_tagVO);
	public Event_TagVO findByPrimaryKey(Integer event_tag_no);
	public List<Event_TagVO> listAllTag();
	public List<String> findAllMem();
	public List<String> findAllEventNo();
	public List<Event_TagVO> randomTag();
	public List<Event_TagVO> findAllByTagName(String event_tag_name);//use event_tag_name return event_tagVO
}
