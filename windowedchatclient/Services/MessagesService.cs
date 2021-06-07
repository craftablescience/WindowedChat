using System.Collections.Generic;
using windowedchatclient.Models;

namespace windowedchatclient.Services
{
    public class MessagesService
    {
        public IEnumerable<ChatMessage> GetAllMessages() => new[]
        {
            new ChatMessage { Message = "Hello world", Sender = "Author" },
            new ChatMessage { Message = "Second message", Sender = "Author2" }
        };
    }
}