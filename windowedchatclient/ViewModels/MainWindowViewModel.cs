using windowedchatclient.Models;
using windowedchatclient.Services;

namespace windowedchatclient.ViewModels
{
    public class MainWindowViewModel : ViewModelBase
    {
        public MessagesViewModel List { get; }

        public MainWindowViewModel()
        {
            List = new MessagesViewModel(System.Array.Empty<ChatMessage>());
        }
        
        public MainWindowViewModel(MessagesService ms)
        {
            List = new MessagesViewModel(ms.GetAllMessages());
        }
    }
}