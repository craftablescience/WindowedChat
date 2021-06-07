using Avalonia.Controls;
using Avalonia.Markup.Xaml;
using System.Runtime.InteropServices;

namespace windowedchatclient.Views.TitleBars
{
    public class WindowsTitleBar : UserControl
    {
        public WindowsTitleBar()
        {
            InitializeComponent();

            if (RuntimeInformation.IsOSPlatform(OSPlatform.Windows) == false)
            {
                IsVisible = false;
            }
            else
            {
                Button minimizeButton = this.FindControl<Button>("MinimizeButton");
                Image windowIcon = this.FindControl<Image>("WindowIcon");

                minimizeButton.Click += MinimizeWindow!;
                windowIcon.DoubleTapped += CloseWindow!;

                //SubscribeToWindowState();
            }
        }

        private void CloseWindow(object sender, Avalonia.Interactivity.RoutedEventArgs e)
        {
            Window hostWindow = (Window) VisualRoot;
            hostWindow.Close();
        }

        private void MinimizeWindow(object sender, Avalonia.Interactivity.RoutedEventArgs e)
        {
            Window hostWindow = (Window) VisualRoot;
            hostWindow.WindowState = WindowState.Minimized;
        }

        /*private async void SubscribeToWindowState()
        {
            Window hostWindow = (Window) VisualRoot;

            while (hostWindow == null)
            {
                hostWindow = (Window) VisualRoot;
                await Task.Delay(50);
            }
        }*/

        private void InitializeComponent()
        {
            AvaloniaXamlLoader.Load(this);
        }
    }
}
