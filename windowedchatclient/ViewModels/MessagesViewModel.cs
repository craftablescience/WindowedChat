using System.Collections.Generic;
using System.Collections.ObjectModel;
using windowedchatclient.Models;

namespace windowedchatclient.ViewModels
{
    public class MessagesViewModel : ViewModelBase
    {
        public MessagesViewModel(IEnumerable<ChatMessage> items)
        { 
            Items = new ObservableCollection<ChatMessage>(items);
        }
        
        public ObservableCollection<ChatMessage> Items { get; }
    }
}