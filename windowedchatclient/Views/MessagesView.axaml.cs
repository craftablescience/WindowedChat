using Avalonia.Controls;
using Avalonia.Markup.Xaml;

namespace windowedchatclient.Views
{
    public class MessagesView : UserControl
    {
        public MessagesView()
        {
            InitializeComponent();
        }

        private void InitializeComponent()
        {
            AvaloniaXamlLoader.Load(this);
        }
    }
}