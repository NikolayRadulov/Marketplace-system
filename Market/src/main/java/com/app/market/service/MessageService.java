package com.app.market.service;

import com.app.market.model.entity.User;

public interface MessageService {

	void sendMessage(User sender, User reciever, String text);
}
