using System;
using System.Collections.Generic;
using System.Threading;
using System.Threading.Tasks;
using Avalonia.Threading;
using DynamicData.Binding;
using windowedchatclient.Models;

namespace windowedchatclient.Services
{
    public class MessagesService
    {
        private readonly List<ChatMessage> _messages;

        public MessagesService()
        {
            _messages = new List<ChatMessage>();
            AddMessage("Connected to Minecraft", "server-name-here");
        }

        public IEnumerable<ChatMessage> GetAllMessages() => _messages;

        public void AddMessage(string sender, string message)
        {
            _messages.Add(new ChatMessage
            {
                Sender = sender,
                Message = message
            });
        }
    }
}