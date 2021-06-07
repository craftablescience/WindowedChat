using Avalonia;
using Avalonia.Controls;
using Avalonia.Controls.ApplicationLifetimes;
using Avalonia.Markup.Xaml;
using windowedchatclient.Services;
using windowedchatclient.ViewModels;
using windowedchatclient.Views;

namespace windowedchatclient
{
    public class App : Application
    {
        public override void Initialize()
        {
            AvaloniaXamlLoader.Load(this);
        }

        public override void OnFrameworkInitializationCompleted()
        {
            if (ApplicationLifetime is IClassicDesktopStyleApplicationLifetime desktop)
            {
                var ms = new MessagesService();
                
                desktop.MainWindow = new MainWindow
                {
                    DataContext = new MainWindowViewModel(ms),
                    MinHeight = 400,
                    MinWidth = 300,
                    MaxHeight = 2000,
                    MaxWidth = 500
                };
            }
            base.OnFrameworkInitializationCompleted();
        }
    }
}